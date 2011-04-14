package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

public abstract class Page {

	public static final int TUPLES_PER_PAGE = 1000;

	public int tuples;

	public int pageID;

	public abstract boolean addTuple(int value);

	public abstract boolean addTuple(float value);

	public abstract boolean addTuple(double value);

	public abstract boolean addTuple(String value);

	public static String sqlName() {
		return null;
	}

	public static int getPageSize() {
		return 0;
	}

	public synchronized void write(RandomAccessFile os) throws IOException {
		// do nothing
	}

	public synchronized void read(RandomAccessFile is) throws IOException {
		// do nothing
	}

	public static Hashtable<String, Class<? extends Page>> types = new Hashtable();

	public abstract boolean isFixedWidth();

	public abstract int getFixedSize();

	public abstract int getMinimumVariableSize();

	public abstract int getMaximumVariableSize();

	public abstract String[] getNames();

	public abstract Class getPageClass();

	public void registerTypes() {
		Page.registerClass(this);
	}

	public static void registerClass(Page type) {
		for(String name : type.getNames())
			types.put(name, type.getClass());
	}

	public static Class<? extends Page> getClassForType(String name) {
		return (Class<? extends Page>) types.get(name);
	}

	public static void registerAll() {
		new BigIntPage().registerTypes();
		new BlobPage().registerTypes();
		new BooleanPage().registerTypes();
		new CharPage().registerTypes();
		new DatePage().registerTypes();
		new DoublePage().registerTypes();
		new IntPage().registerTypes();
		new NumericalPage().registerTypes();
		new RealPage().registerTypes();
		new TimePage().registerTypes();
		new TimestampPage().registerTypes();
		new VarCharPage().registerTypes();
	}

}
