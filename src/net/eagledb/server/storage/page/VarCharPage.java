package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class VarCharPage extends Page {

	public String[] page;

	public VarCharPage() {
		page = new String[Page.TUPLES_PER_PAGE];
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

	@Override
	public synchronized void write(RandomAccessFile os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeBytes(page[i]);
	}

	public static int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
	}

}
