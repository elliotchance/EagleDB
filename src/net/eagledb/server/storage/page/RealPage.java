package net.eagledb.server.storage.page;

public class RealPage extends Page {

	public float[] page;

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

}
