package net.eagledb.server.storage.page;

import java.io.DataOutputStream;
import java.io.IOException;

public class TransactionPage extends Page {

	public int[] transactionID;

	public TransactionPage() {
		transactionID = new int[1000];
	}

	public int getTotalTuples() {
		return tuples;
	}

	public void addTransactionTuple(int transaction) {
		transactionID[tuples++] = transaction;
	}

	public boolean addTuple(int value) {
		// not valid for transation page
		return false;
	}

	public boolean addTuple(float value) {
		// not valid for transation page
		return false;
	}

	@Override
	public synchronized void write(DataOutputStream os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeInt(transactionID[i]);
	}

}
