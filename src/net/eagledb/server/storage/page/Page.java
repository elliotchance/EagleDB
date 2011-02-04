package net.eagledb.server.storage.page;

public abstract class Page {

	public int tuples;

	public abstract boolean addTuple(int value);

}
