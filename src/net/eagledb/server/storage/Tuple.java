package net.eagledb.server.storage;

import java.io.*;

public class Tuple implements Serializable {

	public Object[] attributes;

	public Tuple(int fields) {
		attributes = new Object[fields];
	}

	public void set(int position, int value) {
		attributes[position] = value;
	}

	public void set(int position, float value) {
		attributes[position] = value;
	}

}
