package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DoublePage extends Page {

	static {
		Page.registerClass(new DoublePage());
	}

	public double[] page;

	public DoublePage() {
		page = new double[Page.TUPLES_PER_PAGE];
	}

	public boolean addTuple(int value) {
		page[tuples++] = value;
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = value;
		return true;
	}

	public boolean addTuple(double value) {
		page[tuples++] = value;
		return true;
	}

	@Override
	public synchronized void write(RandomAccessFile os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeDouble(page[i]);
	}

	@Override
	public synchronized void read(RandomAccessFile is) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			page[i] = is.readDouble();
	}

	public static int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
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
		return new String[] { "DOUBLE", "DOUBLE PRECISION" };
	}

	public Class getPageClass() {
		return null;
	}

	public boolean addTuple(String value) {
		page[tuples++] = Double.valueOf(value);
		return true;
	}

}
