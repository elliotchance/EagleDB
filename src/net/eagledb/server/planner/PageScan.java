package net.eagledb.server.planner;

import net.eagledb.server.storage.page.*;

public class PageScan extends PageOperation {
	
	public int buffer;

	public Object value;

	public PageScan(int buffer, int fieldID, PageAction action, Object value) {
		this.buffer = buffer;
		this.fieldID = fieldID;
		this.action = action;
		this.value = value;
	}

	public int getMaxBuffer() {
		return buffer;
	}

	@Override
	public String toString() {
		return "PageScan ( " + fieldID + " " + action + " " + value + " ) => #" + buffer;
	}

	public void run(TransactionPage tp, Page p, boolean[][] buf) {
		if(p instanceof net.eagledb.server.storage.page.IntPage) {
			IntPage page = (IntPage) p;
			int compare = (int) (double) Double.valueOf(value.toString());
			
			if(action == PageAction.ALL) {
				for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
					if(tp.transactionID[i] > 0)
						buf[buffer][i] = true;
				}
				return;
			}

			if(action == PageAction.GREATER_THAN) {
				for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
					if(tp.transactionID[i] > 0)
						buf[buffer][i] = (page.page[i] > compare);
				}
				return;
			}
			
			if(action == PageAction.LESS_THAN) {
				for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
					if(tp.transactionID[i] > 0)
						buf[buffer][i] = (page.page[i] < compare);
				}
				return;
			}
		}
	}

}
