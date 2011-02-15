package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.storage.Table;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.schema.*;

public class Expression {

	public static int MAXIMUM_BUFFERS = 10;

	public ArrayList<PageOperation> operations;

	private int bufferID = 0;

	private int resultBuffer = 0;

	private net.sf.jsqlparser.expression.Expression expression;

	private Table table;

	public Expression(Table table, net.sf.jsqlparser.expression.Expression expression) {
		operations = new ArrayList<PageOperation>();
		this.expression = expression;
		this.table = table;
	}

	private int subparse(net.sf.jsqlparser.expression.Expression ex) throws ExpressionException {
		if(ex instanceof Column) {
			return MAXIMUM_BUFFERS + table.getAttributeLocation(ex.toString());
		}

		if(ex instanceof LongValue) {
			int dest = resultBuffer++;
			operations.add(new PageFill(
				dest,
				Double.valueOf(ex.toString())
			));
			return dest;
		}

		/*if(ex instanceof AndExpression) {
			AndExpression current = (AndExpression) ex;
			int buf1 = subparse(current.getLeftExpression());
			int buf2 = subparse(current.getRightExpression());
			int dest = resultBuffer++;
			operations.add(new PageCompare(buf1, buf2, dest, PageAction.AND));
			return dest;
		}

		if(ex instanceof OrExpression) {
			OrExpression current = (OrExpression) ex;
			int buf1 = subparse(current.getLeftExpression());
			int buf2 = subparse(current.getRightExpression());
			int dest = resultBuffer++;
			operations.add(new PageCompare(buf1, buf2, dest, PageAction.OR));
			return dest;
		}*/

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

			int dest = resultBuffer++;
			operations.add(new PageCompare(dest, lhs, action, rhs));
			return dest;
		}

		throw new ExpressionException(ex);
	}

	public PageOperation[] parse() throws ExpressionException {
		if(expression == null)
			expression = new LongValue("1");
		subparse(expression);

		// convert to array
		PageOperation[] op = new PageOperation[operations.size()];
		for(int i = 0; i < op.length; ++i)
			op[i] = operations.get(i);
		
		return op;
	}

}
