package net.eagledb.server.planner;

import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;

public class PageAttribute extends PageOperation {
	
	public int destination;

	public int source;
	
	public PageAttribute(int destination, int source) {
		this.destination = destination;
		this.source = source;
	}

	public void run(int tuples, FullTableScan fts) {
		Page pageDest = null;
		if(destination >= Expression.MAXIMUM_BUFFERS)
			pageDest = fts.table.getPage(destination - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			pageDest = fts.buffers.get(destination);

		Page pageSource = null;
		if(source >= Expression.MAXIMUM_BUFFERS)
			pageSource = fts.table.getPage(source - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			pageSource = fts.buffers.get(source);

		if(pageSource instanceof DoublePage) {
			DoublePage page = (DoublePage) pageSource;
			for(int i = 0; i < tuples; ++i)
				pageDest.addTuple(page.page[i]);
		}
		else if(pageSource instanceof IntPage) {
			IntPage page = (IntPage) pageSource;
			for(int i = 0; i < tuples; ++i)
				pageDest.addTuple(page.page[i]);
		}
	}

	@Override
	public String toString() {
		return "PageAttribute ( source = " + source + ", destination = " + destination + " )";
	}

}
