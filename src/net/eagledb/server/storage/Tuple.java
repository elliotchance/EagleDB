package net.eagledb.server.storage;

public class Tuple {

	public Object[] attributes;

	public Tuple(int fields) {
		attributes = new Object[fields];
	}

	public void set(int position, int value) {
		attributes[position] = value;
	}

}
