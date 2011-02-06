package net.eagledb.server.storage.page;

public abstract class Page {

	public static final int TUPLES_PER_PAGE = 1000;

	public int tuples;

	public abstract boolean addTuple(int value);

	public abstract boolean addTuple(float value);

}
