package net.eagledb.server.storage;

import java.io.*;

public class Tuple implements Serializable {

	private Object[] attributes;

	public Tuple(int fields) {
		attributes = new Object[fields];
	}

	public void set(int position, int value) {
		attributes[position] = value;
	}

	public void set(int position, float value) {
		attributes[position] = value;
	}

	public void set(int position, Object value) {
		attributes[position] = value;
	}

	public Object get(int position) {
		return attributes[position];
	}

}
