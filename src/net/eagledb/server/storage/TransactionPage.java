package net.eagledb.server.storage;

public class TransactionPage {

	public int[] transactionID;

	private int tuples = 0;

	public TransactionPage() {
		transactionID = new int[1000];
	}

	public int getTotalTuples() {
		return tuples;
	}

	public void addTuple(int transaction) {
		transactionID[tuples++] = transaction;
	}

}
