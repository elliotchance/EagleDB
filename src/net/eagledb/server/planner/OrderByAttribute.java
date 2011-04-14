package net.eagledb.server.planner;

public class OrderByAttribute {

	public int position;

	public boolean isAsc;

	public String expr;

	public OrderByAttribute(int position, boolean isAsc, String expr) {
		this.position = position;
		this.isAsc = isAsc;
		this.expr = expr;
	}

	@Override
	public String toString() {
		return expr;
	}

}
