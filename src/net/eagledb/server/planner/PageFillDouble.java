package net.eagledb.server.planner;

import net.eagledb.server.storage.page.Page;

public class PageFillDouble extends PageOperation {
	
	public int destination;

	public double value;
	
	public PageFillDouble(int destination, double value) {
		this.destination = destination;
		this.value = value;
	}

	public void run(int tuples, FullTableScan fts) {
		Page page = null;
		if(destination >= Expression.MAXIMUM_BUFFERS)
			page = fts.table.getPage(destination - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			page = fts.buffers.get(destination);
		
		for(int i = 0; i < tuples; ++i)
			page.addTuple(value);
	}

	@Override
	public String toString() {
		return "PageFillDouble ( value = " + value + ", destination = " + destination + " )";
	}

}
