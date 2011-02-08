package net.eagledb.server.planner;

public class ExpressionException extends Exception {

	private net.sf.jsqlparser.expression.Expression ex;

	public ExpressionException(net.sf.jsqlparser.expression.Expression ex) {
		this.ex = ex;
	}

	@Override
	public String toString() {
		return "Cannot parse expression: " + ex + "\n                of type: " + ex.getClass().getSimpleName();
	}

}
