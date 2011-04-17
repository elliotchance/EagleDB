package net.eagledb.server.planner;

import java.lang.reflect.Method;
import net.eagledb.server.storage.page.Page;

public class PageUnaryOperation extends PageOperation {

	public int buffer1;

	public int bufferDestination;

	private Method operation;

	public PageUnaryOperation(int bufferDestination, Method operation, int buffer1) {
		this.bufferDestination = bufferDestination;
		this.buffer1 = buffer1;
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "PageUnaryOperation ( #" + buffer1 + " " + action + " ) => " + bufferDestination;
	}

	public void run(int tuples, FullTableScan fts) {
		Page lhs = null;

		if(buffer1 >= Expression.MAXIMUM_BUFFERS)
			lhs = fts.table.getPage(buffer1 - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			lhs = fts.buffers.get(buffer1);

		try {
			operation.invoke(null, fts.buffers.get(bufferDestination), lhs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
