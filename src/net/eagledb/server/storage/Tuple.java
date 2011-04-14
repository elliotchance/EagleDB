package net.eagledb.server.storage;

import java.io.*;

public class Tuple implements Serializable {

	public int tupleID = -1;

	private Object[] attributes;

	public Tuple(int tupleID, int fields) {
		this(fields);
		this.tupleID = tupleID;
	}

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

	public int size() {
		return attributes.length;
	}

	@Override
	public String toString() {
		String r = "Tuple[" + tupleID + "]( ";
		for(Object o : attributes) {
			r += o + " ";
		}
		return r + ")";
	}

}
