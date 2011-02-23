package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class IntPage extends Page {

	public int[] page;

	public IntPage() {
		page = new int[Page.TUPLES_PER_PAGE];
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

	public static void operatorAdd(IntPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] + page2.page[i]);
	}

	public static void operatorAdd(DoublePage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] + page2.page[i]);
	}

	public static void operatorSubtract(IntPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] - page2.page[i]);
	}

	public static void operatorSubtract(DoublePage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] - page2.page[i]);
	}

	public static void operatorMultiply(IntPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] * page2.page[i]);
	}

	public static void operatorMultiply(DoublePage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] * page2.page[i]);
	}

	public static void operatorDivide(IntPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] / page2.page[i]);
	}

	public static void operatorDivide(DoublePage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] / page2.page[i]);
	}

	public static void operatorEqual(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] == page2.page[i]);
	}

	public static void operatorEqual(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] == page2.page[i]);
	}

	public static void operatorNotEqual(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] != page2.page[i]);
	}

	public static void operatorNotEqual(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] != page2.page[i]);
	}

	public static void operatorGreater(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] > page2.page[i]);
	}

	public static void operatorGreater(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] > page2.page[i]);
	}

	public static void operatorLess(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] < page2.page[i]);
	}

	public static void operatorLess(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] < page2.page[i]);
	}

	public static void operatorGreaterEqual(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] >= page2.page[i]);
	}

	public static void operatorGreaterEqual(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] >= page2.page[i]);
	}

	public static void operatorLessEqual(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] <= page2.page[i]);
	}

	public static void operatorLessEqual(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page1.page[i] <= page2.page[i]);
	}

	public static void operatorCast(BooleanPage destination, IntPage page) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = (page.page[i] != 0);
	}

	public static String sqlName() {
		return "INTEGER";
	}

}
