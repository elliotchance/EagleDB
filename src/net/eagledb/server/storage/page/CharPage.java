package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * "CHAR" and "CHARACTER"
 */
public class CharPage extends Page {

	public byte[] page;

	static {
		Page.registerClass(new CharPage());
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
		return new String[] { "CHAR", "CHARACTER" };
	}

	public Class getPageClass() {
		return null;
	}

	public boolean addTuple(int value) {
		page[tuples++] = (byte) value;
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = (byte) value;
		return true;
	}

	public boolean addTuple(double value) {
		page[tuples++] = (byte) value;
		return true;
	}

	@Override
	public synchronized void write(RandomAccessFile os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			os.writeByte(page[i]);
		}
	}

	@Override
	public synchronized void read(RandomAccessFile is) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			page[i] = is.readByte();
		}
	}

	public static int getPageSize() {
		return 1 * Page.TUPLES_PER_PAGE;
	}

	public boolean addTuple(String value) {
		page[tuples++] = (byte) value.charAt(0);
		return true;
	}

}
