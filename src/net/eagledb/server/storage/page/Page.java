package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class Page {

	public static final int TUPLES_PER_PAGE = 1000;

	public int tuples;

	public int pageID;

	public abstract boolean addTuple(int value);

	public abstract boolean addTuple(float value);

	public abstract int getPageSize();

	public synchronized void write(RandomAccessFile os) throws IOException {
		// do nothing
	}

}
