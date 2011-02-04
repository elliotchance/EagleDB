package net.eagledb.server.planner;

public interface PlanItem {

	@Override
	public String toString();

	public void execute();

}
