package net.eagledb.server.planner;

import java.lang.reflect.Method;

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

	public void run(int tuples, FullTableScan fts) {
		Object[] arg = new Object[2 + args.length];
		arg[0] = tuples;
		arg[1] = fts.buffers.get(bufferDestination);

		for(int i = 0; i < args.length; ++i) {
			if(args[i] >= Expression.MAXIMUM_BUFFERS) {
				arg[i + 2] = fts.table.getPage(args[i] - Expression.MAXIMUM_BUFFERS, fts.pageID, fts.cost);
			}
			else {
				arg[i + 2] = fts.buffers.get(args[i]);
			}
		}

		try {
			// varargs
			if(function.getParameterTypes()[0].equals(Object[].class)) {
				function.invoke(null, (Object) arg);
			}
			else {
				function.invoke(null, (Object[]) arg);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
