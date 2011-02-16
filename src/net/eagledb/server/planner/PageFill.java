package net.eagledb.server.planner;

import net.eagledb.server.storage.page.Page;

public class PageFill extends PageOperation {
	
	public int destination;

	public Object value;
	
	public PageFill(int destination, Object value) {
		this.destination = destination;
		this.value = value;
	}

	public void run(FullTableScan fts) {
		Page page = null;
		if(destination >= Expression.MAXIMUM_BUFFERS)
			page = fts.table.getPage(destination - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			page = fts.buffers.get(destination);
		
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			page.addTuple(Float.valueOf(value.toString()));
	}

	@Override
	public String toString() {
		return "FillPage ( value = " + value + ", destination = " + destination + " )";
	}

}
