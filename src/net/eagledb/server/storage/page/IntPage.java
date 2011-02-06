package net.eagledb.server.storage.page;

import java.util.ArrayList;
import net.eagledb.server.planner.*;
import net.eagledb.server.storage.Tuple;

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

	public void scan(TransactionPage tp, ArrayList<Tuple> tuples, PageAction action, Object value) {
		int v = Integer.valueOf(value.toString()), i = 0;
		for(int j = 0; j < Page.TUPLES_PER_PAGE; ++j) {
			if(tp.transactionID[j] > 0 && page[j] > v)
				tuples.add(new Tuple(j, 2));
		}
	}

}
