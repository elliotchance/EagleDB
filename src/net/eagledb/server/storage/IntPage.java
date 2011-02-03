package net.eagledb.server.storage;

public class IntPage extends Page {

	public int[] page;

	public IntPage() {
		page = new int[1000];
	}

	public boolean addTuple(int value) {
		page[tuples++] = value;
		return true;
	}

}
