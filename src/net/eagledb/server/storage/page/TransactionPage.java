package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TransactionPage extends Page {

	public int[] transactionID;

	public TransactionPage() {
		transactionID = new int[Page.TUPLES_PER_PAGE];
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
	public synchronized void write(RandomAccessFile os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeInt(transactionID[i]);
	}

	public int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
	}

}
