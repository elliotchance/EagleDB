package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * "TIMESTAMP"
 */
public class TimestampPage extends Page {

	public int[] page;

	static {
		Page.registerClass(new TimestampPage());
	}

	public boolean isFixedWidth() {
		return true;
	}

	public int getFixedSize() {
		return 8;
	}

	public int getMinimumVariableSize() {
		return 0;
	}

	public int getMaximumVariableSize() {
		return 0;
	}

	public String[] getNames() {
		return new String[] { "TIMESTAMP" };
	}

	public Class getPageClass() {
		return null;
	}

	public boolean addTuple(int value) {
		page[tuples++] = value;
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = (int) value;
		return true;
	}

	public boolean addTuple(double value) {
		page[tuples++] = (int) value;
		return true;
	}

	@Override
	public synchronized void write(RandomAccessFile os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			os.writeLong(page[i]);
		}
	}

	@Override
	public synchronized void read(RandomAccessFile is) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			page[i] = is.readInt();
		}
	}

	public static int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
	}

	public boolean addTuple(String value) {
		page[tuples++] = 0;
		return true;
	}

}
