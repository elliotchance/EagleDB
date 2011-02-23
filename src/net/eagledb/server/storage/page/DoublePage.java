package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DoublePage extends Page {

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

	public static void operatorEqual(BooleanPage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] == page2.page[i]);
	}

	public static void operatorCast(BooleanPage destination, DoublePage page) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page.page[i] != 0.0);
	}

	public static String sqlName() {
		return "DOUBLE";
	}

}
