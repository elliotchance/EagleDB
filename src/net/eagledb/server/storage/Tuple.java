package net.eagledb.server.storage;

import java.util.ArrayList;

public class Tuple {

	public Object[] attributes;

	public Tuple(ArrayList<Field> fields) {
		attributes = new Object[fields.size()];
	}

	public void set(int position, int value) {
		attributes[position] = value;
	}

}
