package net.eagledb.server.storage.page;

import net.eagledb.server.planner.*;

public class RealPage extends Page {

	public float[] page;

	public RealPage next = null;

	public RealPage() {
		page = new float[1000];
	}

	public boolean addTuple(int value) {
		page[tuples++] = value;
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = value;
		return true;
	}

	public int[] scan(TransactionPage tp, PageScanAction action, Object value) {
		return new int[0];
	}

}
