package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RealPage extends Page {

	static {
		Page.registerClass(new RealPage());
	}

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

	public boolean addTuple(double value) {
		page[tuples++] = (float) value;
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

	public static String sqlName() {
		return "REAL";
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
		return new String[] { "REAL", "FLOAT" };
	}

	public Class getPageClass() {
		return net.eagledb.server.storage.page.RealPage.class;
	}

	public boolean addTuple(String value) {
		page[tuples++] = Float.valueOf(value);
		return true;
	}

}
