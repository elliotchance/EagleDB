package net.eagledb.server.storage.index;

public class KeyNode {

	private int key;
	private int data;

	KeyNode(int key, int data) {
		this.key = key;
		this.data = data;
	}

	int getKey() {
		return key;
	}

	int getObj() {
		return data;
	}

	@Override
	public String toString() {
		return "KeyNode(key = " + key + ", data = " + data + ")";
	}

}
