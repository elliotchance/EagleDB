package net.eagledb.server.planner;

public class PlanItemCost {

	public int estimateMinimumTimerons = 0;

	public int estimateMaximumTimerons = 0;

	public long realMillis = 0;

	public int pagesReadFromCache = 0;

	public int pagesReadFromDisk = 0;

}
