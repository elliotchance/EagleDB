package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TransactionPage extends Page {

	public long[] createTransactionID;

	public long[] expireTransactionID;

	public static final long EXPIRE_NEVER = Long.MAX_VALUE;

	public TransactionPage() {
		createTransactionID = new long[Page.TUPLES_PER_PAGE];
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			createTransactionID[i] = Long.MAX_VALUE;
		
		expireTransactionID = new long[Page.TUPLES_PER_PAGE];
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			expireTransactionID[i] = EXPIRE_NEVER;
	}

	public int getTotalTuples() {
		return tuples;
	}

	public void addTransactionTuple(long transaction) {
		createTransactionID[tuples++] = transaction;
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
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			os.writeLong(createTransactionID[i]);
			os.writeLong(expireTransactionID[i]);
		}
	}

	@Override
	public synchronized void read(RandomAccessFile is) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			createTransactionID[i] = is.readLong();
			expireTransactionID[i] = is.readLong();
		}
	}

	public static int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
	}

	public static String sqlName() {
		return "TRANSACTION";
	}

}
