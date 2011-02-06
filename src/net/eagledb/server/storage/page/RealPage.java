package net.eagledb.server.storage.page;

import java.util.ArrayList;
import net.eagledb.server.planner.*;
import net.eagledb.server.storage.Tuple;

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

	public void scan(TransactionPage tp, ArrayList<Tuple> tuples, PageScanAction action, Object value) {
	}

}
