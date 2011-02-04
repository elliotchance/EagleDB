package net.eagledb.server.storage.page;

import net.eagledb.server.planner.*;

public class IntPage extends Page {

	public int[] page;

	public IntPage next = null;

	public IntPage() {
		page = new int[1000];
	}

	public boolean addTuple(int value) {
		page[tuples++] = value;
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = (int) value;
		return true;
	}

	public int[] scan(TransactionPage tp, PageScanAction action, Object value) {
		int v = Integer.valueOf(value.toString()), count = 0, i = 0;

		for(int j = 0; j < Page.TUPLES_PER_PAGE; ++j) {
			if(tp.transactionID[j] > 0 && page[j] > v)
				++count;
		}

		int[] r = new int[count];
		for(int j = 0; j < Page.TUPLES_PER_PAGE; ++j) {
			if(tp.transactionID[j] > 0 && page[j] > v)
				r[i++] = j;
		}

		return r;
	}

}
