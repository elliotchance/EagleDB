package net.eagledb.server.planner;

import java.lang.reflect.Method;
import net.eagledb.server.storage.page.Page;

public class PageFunction extends PageOperation {

	public int[] args;

	public int bufferDestination;

	private Method function;

	public PageFunction(int bufferDestination, Method function, int[] args) {
		this.bufferDestination = bufferDestination;
		this.args = args;
		this.function = function;
	}

	@Override
	public String toString() {
		return "PageFunction (  ) => " + bufferDestination;
	}

	public void run(FullTableScan fts) {
		Page[] arg = new Page[1 + args.length];
		arg[0] = fts.buffers.get(bufferDestination);

		for(int i = 0; i < args.length; ++i) {
			if(args[i] >= Expression.MAXIMUM_BUFFERS)
				arg[i + 1] = fts.table.getPage(args[i] - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
			else
				arg[i + 1] = fts.buffers.get(args[i]);
		}

		try {
			function.invoke(null, (Object[]) arg);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
