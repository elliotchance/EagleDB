package net.eagledb.server.storage.page;

import java.util.ArrayList;
import net.eagledb.server.planner.*;
import net.eagledb.server.storage.Tuple;

public class IntPage extends Page {

	public int[] page;

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

}
