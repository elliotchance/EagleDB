package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * "TIME"
 */
public class TimePage extends Page {

	public int[] page;

	static {
		Page.registerClass(new TimePage());
	}

	public boolean isFixedWidth() {
		return true;
	}

	public int getFixedSize() {
		return 4;
	}

	public int getMinimumVariableSize() {
		return 0;
	}

	public int getMaximumVariableSize() {
		return 0;
	}

	public String[] getNames() {
		return new String[] { "TIME" };
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
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeInt(page[i]);
	}

	@Override
	public synchronized void read(RandomAccessFile is) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			page[i] = is.readInt();
	}

	public static int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
	}

}
