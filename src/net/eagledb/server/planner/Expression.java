package net.eagledb.server.planner;

import java.util.ArrayList;
import net.sf.jsqlparser.expression.operators.conditional.*;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.eagledb.server.storage.Table;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.schema.*;

public class Expression {

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
		if(ex instanceof AndExpression) {
			AndExpression current = (AndExpression) ex;
			int buf1 = subparse(current.getLeftExpression());
			int buf2 = subparse(current.getRightExpression());
			int dest = resultBuffer++;
			operations.add(new PageCompare(buf1, buf2, dest, PageAction.AND));
			return dest;
		}
		
		if(ex instanceof GreaterThan) {
			GreaterThan current = (GreaterThan) ex;

			// check if the left side is a column and the right side is a value
			if(current.getLeftExpression() instanceof Column && current.getRightExpression() instanceof LongValue) {
				int dest = resultBuffer++;
				operations.add(new PageScan(
					dest,
					table.getAttributeLocation(current.getLeftExpression().toString()),
					PageAction.GREATER_THAN,
					Double.valueOf(current.getRightExpression().toString())
				));
				return dest;
			}
		}

		if(ex instanceof MinorThan) {
			MinorThan current = (MinorThan) ex;

			// check if the left side is a column and the right side is a value
			if(current.getLeftExpression() instanceof Column && current.getRightExpression() instanceof LongValue) {
				int dest = resultBuffer++;
				operations.add(new PageScan(
					dest,
					table.getAttributeLocation(current.getLeftExpression().toString()),
					PageAction.LESS_THAN,
					Double.valueOf(current.getRightExpression().toString())
				));
				return dest;
			}
		}

		/*if(ex instanceof MinorThan) {
			MinorThan current = (MinorThan) ex;
			int buf1 = subparse(current.getLeftExpression());
			int buf2 = subparse(current.getRightExpression());
			int dest = resultBuffer++;
			operations.add(new PageScan(dest, table.getAttributeLocation("id"), PageAction.LESS_THAN, 42));
			return dest;
		}*/

		throw new ExpressionException(ex);
	}

	public PageOperation[] parse() throws ExpressionException {
		/*operations.add();
		operations.add(new PageScan(1, table.getAttributeLocation("id"), PageAction.LESS_THAN, 40));
		operations.add(new PageCompare(0, 1, 2, PageAction.AND));*/
		subparse(expression);

		// convert to array
		PageOperation[] op = new PageOperation[operations.size()];
		for(int i = 0; i < op.length; ++i)
			op[i] = operations.get(i);
		
		return op;
	}

}
