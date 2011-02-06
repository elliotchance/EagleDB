package net.eagledb.server.storage.page;

import java.util.ArrayList;
import net.eagledb.server.planner.*;
import net.eagledb.server.storage.Tuple;

public class VarCharPage extends Page {

	public String[] page;

	public VarCharPage() {
		page = new String[1000];
	}

	public boolean addTuple(int value) {
		page[tuples++] = String.valueOf(value);
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = String.valueOf(value);
		return true;
	}

	public boolean addTuple(String value) {
		page[tuples++] = value;
		return true;
	}

	public void scan(TransactionPage tp, ArrayList<Tuple> tuples, PageScanAction action, Object value) {
	}

}
