package net.eagledb.server.storage.page;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TransactionPage extends Page {

	public long[] createTransactionID;

	public long[] expireTransactionID;

	public static final long EXPIRE_NEVER = Long.MAX_VALUE;

	public TransactionPage() {
		createTransactionID = new long[Page.TUPLES_PER_PAGE];
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			createTransactionID[i] = Long.MAX_VALUE;
		}
		
		expireTransactionID = new long[Page.TUPLES_PER_PAGE];
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			expireTransactionID[i] = EXPIRE_NEVER;
		}
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

	public boolean addTuple(double value) {
		// not valid for transaction page
		return true;
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

	//

	public boolean isFixedWidth() {
		return true;
	}

	public int getFixedSize() {
		return 4;
	}

	public int getMinimumVariableSize() {
		return 4;
	}

	public int getMaximumVariableSize() {
		return 4;
	}

	public String[] getNames() {
		return new String[] { };
	}

	public Class getPageClass() {
		return net.eagledb.server.storage.page.TransactionPage.class;
	}

	public boolean addTuple(String value) {
		// do nothing
		return true;
	}

}
