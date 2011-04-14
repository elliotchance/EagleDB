package net.eagledb.server.planner.orderby;

public abstract class Orderable {

	public int[] tupleIDs;
	
	public abstract void sortAscending();

	public abstract void sortDescending();

}
