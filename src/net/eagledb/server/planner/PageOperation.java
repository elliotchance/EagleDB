package net.eagledb.server.planner;

public abstract class PageOperation {

	public int bufferPage;

	public PageAction action;

	public abstract void run(FullTableScan fts);

	@Override
	public abstract String toString();

}
