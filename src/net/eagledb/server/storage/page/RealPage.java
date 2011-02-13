package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RealPage extends Page {

	public float[] page;

	public RealPage() {
		page = new float[Page.TUPLES_PER_PAGE];
	}

	public boolean addTuple(int value) {
		page[tuples++] = value;
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = value;
		return true;
	}

	@Override
	public synchronized void write(RandomAccessFile os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeFloat(page[i]);
	}

	@Override
	public synchronized void read(RandomAccessFile is) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			page[i] = is.readFloat();
	}

	public static int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
	}

}
