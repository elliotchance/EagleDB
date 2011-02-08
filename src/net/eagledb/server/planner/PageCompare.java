package net.eagledb.server.planner;

import net.eagledb.server.storage.page.*;

public class PageCompare extends PageOperation {

	public int buffer1;

	public int buffer2;

	public int bufferDestination;

	public PageCompare(int buffer1, int buffer2, int bufferDestination, PageAction action) {
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		this.bufferDestination = bufferDestination;
		this.action = action;
	}

	public int getMaxBuffer() {
		return Math.max(buffer1, Math.max(buffer2, bufferDestination));
	}

	@Override
	public String toString() {
		return "PageCompare ( #" + buffer1 + " " + action + " #" + buffer2 + " ) => " + bufferDestination;
	}

	public void run(TransactionPage tp, Page p, boolean[][] buf) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			buf[bufferDestination][i] = buf[buffer1][i] && buf[buffer2][i];
	}

}
