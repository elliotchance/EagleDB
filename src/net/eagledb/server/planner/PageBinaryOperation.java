package net.eagledb.server.planner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.eagledb.server.storage.page.Page;

public class PageBinaryOperation extends PageOperation {

	public int buffer1;

	public int buffer2;

	public int bufferDestination;

	private Method operation;

	public PageBinaryOperation(int bufferDestination, Method operation, int buffer1, int buffer2) {
		this.bufferDestination = bufferDestination;
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "PageBinaryOperation ( #" + buffer1 + " " + action + " #" + buffer2 + " ) => " + bufferDestination;
	}

	public void run(int tuples, FullTableScan fts) {
		Page lhs = null, rhs = null;

		if(buffer1 >= Expression.MAXIMUM_BUFFERS)
			lhs = fts.table.getPage(buffer1 - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			lhs = fts.buffers.get(buffer1);

		if(buffer2 >= Expression.MAXIMUM_BUFFERS)
			rhs = fts.table.getPage(buffer2 - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			rhs = fts.buffers.get(buffer2);

		try {
			operation.invoke(null, fts.buffers.get(bufferDestination), lhs, rhs);
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch(InvocationTargetException e) {
			e.printStackTrace();
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
