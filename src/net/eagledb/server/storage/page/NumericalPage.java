package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * "NUMERICAL" and "DECIMAL"
 */
public class NumericalPage extends Page {

	public int[] page;

	static {
		Page.registerClass(new NumericalPage());
	}

	public boolean isFixedWidth() {
		return false;
	}

	public int getFixedSize() {
		return 4;
	}

	public int getMinimumVariableSize() {
		return 1;
	}

	public int getMaximumVariableSize() {
		return 1000;
	}

	public String[] getNames() {
		return new String[] { "NUMERICAL", "DECIMAL" };
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

	public boolean addTuple(String value) {
		page[tuples++] = Integer.valueOf(value);
		return true;
	}

}
