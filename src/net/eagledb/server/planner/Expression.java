package net.eagledb.server.planner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Index;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.page.BooleanPage;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.schema.Column;

public class Expression {

	/**
	 * The maxiumum allowed buffers. This is "up to" so buffers are not allocated if not needed. A location ID greater
	 * than MAXIMUM_BUFFERS will mean a field at an index.
	 */
	public static int MAXIMUM_BUFFERS = 10;

	public ArrayList<PageOperation> operations;

	private net.sf.jsqlparser.expression.Expression expression;

	/**
	 * Table reference.
	 */
	private Table table;

	private Database database;

	public ArrayList<Page> buffers = new ArrayList<Page>();

	protected Index bestIndex = null;

	protected Object bestIndexValue = null;

	public Expression(Table table, Database database, net.sf.jsqlparser.expression.Expression expression) {
		operations = new ArrayList<PageOperation>();
		this.expression = expression;
		this.database = database;
		this.table = table;
	}

	@Override
	public String toString() {
		return expression.toString();
	}

	public Index getBestIndex() {
		return bestIndex;
	}

	public Object getBestIndexValue() {
		return bestIndexValue;
	}

	private int subparse(net.sf.jsqlparser.expression.Expression ex, boolean topLevel, boolean preferFloating)
		throws ExpressionException {
		// a singular table column
		if(ex instanceof Column && topLevel) {
			int location = table.getAttributeLocation(ex.toString());
			Class type = table.getAttributes()[location].getPageType();

			if(type.equals(net.eagledb.server.storage.page.IntPage.class))
				buffers.add(new IntPage());
			if(type.equals(net.eagledb.server.storage.page.DoublePage.class))
				buffers.add(new DoublePage());

			int dest = buffers.size() - 1;
			operations.add(new PageAttribute(
				dest,
				MAXIMUM_BUFFERS + location
			));
			return dest;
		}

		// table column
		if(ex instanceof Column)
			return MAXIMUM_BUFFERS + table.getAttributeLocation(ex.toString());

		// integer constant value
		if(ex instanceof LongValue) {
			// preferFloating is to stop division between integers also returning an integer
			if(preferFloating)
				buffers.add(new DoublePage());
			else
				buffers.add(new IntPage());

			int dest = buffers.size() - 1;
			operations.add(new PageFill(
				dest,
				Double.valueOf(ex.toString())
			));
			return dest;
		}

		// floating-point constant value
		if(ex instanceof DoubleValue) {
			buffers.add(new DoublePage());
			int dest = buffers.size() - 1;
			operations.add(new PageFill(
				dest,
				Double.valueOf(ex.toString())
			));
			return dest;
		}

		// + - * / < > <= >= = != <>
		if(ex instanceof BinaryExpression) {
			BinaryExpression current = (BinaryExpression) ex;

			// render each side
			boolean useFloating = (ex instanceof Division);
			int lhs = subparse(current.getLeftExpression(), false, useFloating);
			int rhs = subparse(current.getRightExpression(), false, useFloating);

			// get the PageAction
			PageAction action = null;
			for(PageAction a : PageAction.values()) {
				if(a.toString().equals(current.getStringExpression())) {
					action = a;
					break;
				}
			}
			if(action == null)
				throw new ExpressionException(ex);

			Class lhsClass = null, rhsClass = null;

			if(lhs < Expression.MAXIMUM_BUFFERS)
				lhsClass = buffers.get(lhs).getClass();
			else
				lhsClass = table.getAttributes()[lhs - Expression.MAXIMUM_BUFFERS].pages.get(0).getClass();

			if(rhs < Expression.MAXIMUM_BUFFERS)
				rhsClass = buffers.get(rhs).getClass();
			else
				rhsClass = table.getAttributes()[rhs - Expression.MAXIMUM_BUFFERS].pages.get(0).getClass();
			
			Method operator = Operator.getMethodForOperator(lhsClass, action, rhsClass);
			if(operator == null)
				throw new OperatorException(lhsClass, action, rhsClass, ex);

			// look for an index
			// index lookups only work when the expression is in the form of "ATTRIBUTE OPERATOR VALUE"
			if(lhs >= MAXIMUM_BUFFERS && current.getRightExpression() instanceof LongValue) {
				String indexDef = table.getName() + "(" + table.getAttributes()[lhs - MAXIMUM_BUFFERS].getName() +
					")";
				for(Index index : database.getIndexes()) {
					if(index.getDefinition().equals(indexDef)) {
						bestIndex = index;
						bestIndexValue = Integer.valueOf(current.getRightExpression().toString());
						break;
					}
				}
			}

			int dest = -1;
			try {
				Class returnType = operator.getParameterTypes()[0];
				buffers.add((Page) returnType.newInstance());
				dest = buffers.size() - 1;
				operations.add(new PageBinaryOperation(dest, operator, lhs, rhs));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return dest;
		}

		// functions
		if(ex instanceof net.sf.jsqlparser.expression.Function) {
			net.sf.jsqlparser.expression.Function current = (net.sf.jsqlparser.expression.Function) ex;
			
			// deal with the argument
			net.sf.jsqlparser.expression.Expression arg1 =
				(net.sf.jsqlparser.expression.Expression) current.getParameters().getExpressions().get(0);
			int argument = subparse(arg1, false, false);

			// try and find the function
			Class argType = buffers.get(argument).getClass();
			String functionName = current.getName().toUpperCase();
			Function function = Functions.findFunction(functionName, argType);

			// cannot find the function
			if(function == null)
				throw new FunctionException(new Function(functionName, null, null, argType), ex);

			int dest = -1;
			try {
				buffers.add((Page) function.returnType.newInstance());
				dest = buffers.size() - 1;
				operations.add(new PageFunction(dest, function.method, argument));
			}
			catch(Exception e) {
				e.printStackTrace();
			}

			return dest;
		}

		throw new ExpressionException(ex);
	}

	public PageOperation[] parse(boolean castToBoolean) throws ExpressionException {
		if(expression == null)
			expression = new LongValue("1");
		subparse(expression, true, false);

		// the last Page type must be a boolean if we are using the expression in a WHERE for example
		if(castToBoolean) {
			if(buffers.get(buffers.size() - 1).getClass() != BooleanPage.class) {
				buffers.add(new BooleanPage());
				Method operator = Operator.getMethodForOperator(buffers.get(buffers.size() - 2).getClass(), PageAction.CAST,
					null);
				if(operator == null)
					throw new OperatorException(buffers.get(buffers.size() - 2).getClass(), PageAction.CAST, null,
						expression);
				operations.add(new PageUnaryOperation(buffers.size() - 1, operator, buffers.size() - 2));
			}
		}

		// convert to array
		PageOperation[] op = new PageOperation[operations.size()];
		for(int i = 0; i < op.length; ++i)
			op[i] = operations.get(i);
		
		return op;
	}

}
