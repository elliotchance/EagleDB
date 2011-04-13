package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class BooleanPage extends Page {

	static {
		Page.registerClass(new BooleanPage());
	}

	public boolean[] page;

	public BooleanPage() {
		page = new boolean[Page.TUPLES_PER_PAGE];
	}

	public boolean addTuple(int value) {
		page[tuples++] = (value != 0);
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = (value != 0.0f);
		return true;
	}

	public boolean addTuple(double value) {
		page[tuples++] = (value != 0.0);
		return true;
	}

	@Override
	public synchronized void write(RandomAccessFile os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeBoolean(page[i]);
	}

	@Override
	public synchronized void read(RandomAccessFile is) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			page[i] = is.readBoolean();
	}

	public static int getPageSize() {
		return 1 * Page.TUPLES_PER_PAGE;
	}

	public static String sqlName() {
		return "BOOLEAN";
	}

	public boolean isFixedWidth() {
		return false;
	}

	public int getFixedSize() {
		return 1;
	}

	public int getMinimumVariableSize() {
		return 0;
	}

	public int getMaximumVariableSize() {
		return java.lang.Integer.MAX_VALUE;
	}

	public String[] getNames() {
		return new String[] { "BOOL", "BOOLEAN" };
	}

	public Class getPageClass() {
		return null;
	}

}
