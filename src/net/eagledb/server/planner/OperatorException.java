package net.eagledb.server.planner;

public class OperatorException extends ExpressionException {

	private net.sf.jsqlparser.expression.Expression ex;

	private Class lhs;

	private Class rhs;

	private PageAction op;

	public OperatorException(Class lhs, PageAction op, Class rhs, net.sf.jsqlparser.expression.Expression ex) {
		super(ex);
		this.ex = ex;
		this.lhs = lhs;
		this.rhs = rhs;
		this.op = op;
	}

	@Override
	public String toString() {
		String r = "No operator for expression (" + ex + ") using types (";
		if(lhs != null) {
			r += lhs.getSimpleName();
		}
		r += " " + op + " ";
		if(rhs != null) {
			r += rhs.getSimpleName();
		}
		return r + ")";
	}

}
