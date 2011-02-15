package net.eagledb.server.planner;

import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;

public class PageCompare extends PageOperation {

	public int buffer1;

	public int buffer2;

	public int bufferDestination;

	public PageCompare(int bufferDestination, int buffer1, PageAction action, int buffer2) {
		this.bufferDestination = bufferDestination;
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		this.action = action;
	}

	public int getMaxBuffer() {
		int max = 0;
		if(buffer1 < Expression.MAXIMUM_BUFFERS && buffer1 > max)
			max = buffer1;
		if(buffer2 < Expression.MAXIMUM_BUFFERS && buffer2 > max)
			max = buffer2;
		if(bufferDestination < Expression.MAXIMUM_BUFFERS && bufferDestination > max)
			max = bufferDestination;
		return max;
	}

	@Override
	public String toString() {
		return "PageCompare ( #" + buffer1 + " " + action + " #" + buffer2 + " ) => " + bufferDestination;
	}

	public void run(FullTableScan fts) {
		Page lhs = null, rhs = null;

		if(buffer1 >= Expression.MAXIMUM_BUFFERS)
			lhs = fts.table.getPage(buffer1 - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			lhs = fts.buffer[buffer1];

		if(buffer2 >= Expression.MAXIMUM_BUFFERS)
			rhs = fts.table.getPage(buffer2 - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			rhs = fts.buffer[buffer2];

		IntPage lhs2 = (IntPage) lhs;
		IntPage rhs2 = (IntPage) rhs;
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			if(action == PageAction.PLUS)
				fts.buffer[bufferDestination].addTuple(lhs2.page[i] + rhs2.page[i]);
			else if(action == PageAction.EQUAL)
				fts.buffer[bufferDestination].addTuple((lhs2.page[i] == rhs2.page[i]) ? 1 : 0);
			else if(action == PageAction.NOT_EQUAL)
				fts.buffer[bufferDestination].addTuple((lhs2.page[i] != rhs2.page[i]) ? 1 : 0);
			else if(action == PageAction.GREATER_THAN)
				fts.buffer[bufferDestination].addTuple((lhs2.page[i] > rhs2.page[i]) ? 1 : 0);
			else if(action == PageAction.LESS_THAN)
				fts.buffer[bufferDestination].addTuple((lhs2.page[i] < rhs2.page[i]) ? 1 : 0);
			else if(action == PageAction.GREATER_THAN_EQUAL)
				fts.buffer[bufferDestination].addTuple((lhs2.page[i] >= rhs2.page[i]) ? 1 : 0);
			else if(action == PageAction.LESS_THAN_EQUAL)
				fts.buffer[bufferDestination].addTuple((lhs2.page[i] <= rhs2.page[i]) ? 1 : 0);
		}
	}

}
