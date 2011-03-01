package net.eagledb.server.planner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Index;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.page.BooleanPage;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.Page;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.LongValue;
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

	private Index bestIndex = null;

	public Expression(Table table, Database database, net.sf.jsqlparser.expression.Expression expression) {
		operations = new ArrayList<PageOperation>();
		this.expression = expression;
		this.database = database;
		this.table = table;
	}

	public Index getBestIndex() {
		return bestIndex;
	}

	private int subparse(net.sf.jsqlparser.expression.Expression ex) throws ExpressionException {
		// table column
		if(ex instanceof Column) {
			return MAXIMUM_BUFFERS + table.getAttributeLocation(ex.toString());
		}

		// constant value
		if(ex instanceof LongValue || ex instanceof DoubleValue) {
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
			int lhs = subparse(current.getLeftExpression());
			int rhs = subparse(current.getRightExpression());

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
			if(lhs >= MAXIMUM_BUFFERS) {
				String indexDef = table.getName() + "(" + table.getAttributes()[lhs - MAXIMUM_BUFFERS].getName() +
					")";
				for(Index index : database.getIndexes()) {
					if(index.getDefinition().equals(indexDef)) {
						bestIndex = index;
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

		throw new ExpressionException(ex);
	}

	public PageOperation[] parse() throws ExpressionException {
		if(expression == null)
			expression = new LongValue("1");
		subparse(expression);

		// the last Page type must be a boolean
		if(buffers.get(buffers.size() - 1).getClass() != BooleanPage.class) {
			buffers.add(new BooleanPage());
			Method operator = Operator.getMethodForOperator(buffers.get(buffers.size() - 2).getClass(), PageAction.CAST,
				null);
			if(operator == null)
				throw new OperatorException(buffers.get(buffers.size() - 2).getClass(), PageAction.CAST, null,
					expression);
			operations.add(new PageUnaryOperation(buffers.size() - 1, operator, buffers.size() - 2));
		}

		// convert to array
		PageOperation[] op = new PageOperation[operations.size()];
		for(int i = 0; i < op.length; ++i)
			op[i] = operations.get(i);
		
		return op;
	}

}
