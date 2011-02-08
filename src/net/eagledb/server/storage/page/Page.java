package net.eagledb.server.storage.page;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Page {

	public static final int TUPLES_PER_PAGE = 1000;

	public int tuples;

	public abstract boolean addTuple(int value);

	public abstract boolean addTuple(float value);

	public synchronized void write(DataOutputStream os) throws IOException {
		// do nothing
	}

}
