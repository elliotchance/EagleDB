package net.eagledb.server.planner;

import java.lang.reflect.Method;
import net.eagledb.server.storage.page.Page;

public class PageFunction extends PageOperation {

	public int arg1;

	public int bufferDestination;

	private Method function;

	public PageFunction(int bufferDestination, Method function, int arg1) {
		this.bufferDestination = bufferDestination;
		this.arg1 = arg1;
		this.function = function;
	}

	@Override
	public String toString() {
		return "PageFunction (  ) => " + bufferDestination;
	}

	public void run(FullTableScan fts) {
		Page arg = null;

		if(arg1 >= Expression.MAXIMUM_BUFFERS)
			arg = fts.table.getPage(arg1 - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
		else
			arg = fts.buffers.get(arg1);

		try {
			function.invoke(null, fts.buffers.get(bufferDestination), arg);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
