package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class VarCharPage extends Page {

	static {
		Page.registerClass(new VarCharPage());
	}

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

	public boolean addTuple(double value) {
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

	public static String sqlName() {
		return "VARCHAR";
	}

	public boolean isFixedWidth() {
		return false;
	}

	public int getFixedSize() {
		return 4;
	}

	public int getMinimumVariableSize() {
		return 0;
	}

	public int getMaximumVariableSize() {
		return java.lang.Integer.MAX_VALUE;
	}

	public String[] getNames() {
		return new String[] { "VARCHAR", "CHARACTER VARYING" };
	}

	public Class getPageClass() {
		return null;
	}

}
