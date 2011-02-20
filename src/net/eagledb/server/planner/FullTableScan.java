package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import java.util.*;

public class FullTableScan implements PlanItem {

	public Table table;

	private Database selectedDatabase;

	public PageOperation[] operations;

	private ArrayList<Tuple> tuples;

	private int tupleSize;

	private String clause;

	public PlanItemCost cost = new PlanItemCost();

	public ArrayList<Page> buffers;

	public int pageID;

	/**
	 * Initialise with execute(). This array holds all the transaction IDs currently in progress before the full table
	 * scan starts.
	 */
	private long[] transactionIDs;

	public FullTableScan(Database selectedDatabase, Table table, int tupleSize, String clause,
		PageOperation[] operations, ArrayList<Page> buffers) {
		this.selectedDatabase = selectedDatabase;
		this.table = table;
		this.operations = operations;
		this.tupleSize = tupleSize;
		this.clause = clause;
		this.buffers = buffers;
		estimateCost();
	}

	private void estimateCost() {
		/*int totalBuffers = 0;
		for(int i = 0; i < operations.length; ++i) {
			if(operations[i].getMaxBuffer() > totalBuffers)
				totalBuffers += operations[i].getMaxBuffer();
		}

		// assume a full table scan reads every page, multiple that by how much work is done on each page
		cost.estimateMinimumTimerons = cost.estimateMaximumTimerons = table.getTotalPages() * totalBuffers;*/
	}

	@Override
	public String toString() {
		return "FullTableScan ( " + table.getName() + ": " + clause + " )";
	}

	private boolean inTransactionIDs(long XID) {
		for(int i = 0; i < transactionIDs.length; ++i) {
			if(transactionIDs[i] == XID)
				return true;
		}
		return false;
	}

	public void execute(ArrayList<Tuple> tuples, long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();

		// show buffers
		//for(int i = 0; i < buffers.size(); ++i)
		//	System.out.println("buffer[" + i + "] = " + buffers.get(i));

		// run operations
		for(pageID = 0; pageID < table.getTotalPages(); ++pageID) {
			for(PageOperation operation : operations)
				operation.run(this);

			TransactionPage tp = table.getTransactionPage(pageID, cost);
			BooleanPage result = (BooleanPage) buffers.get(buffers.size() - 1);
			for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
				// Display all the row versions that match the following criteria:
				if(
					// * The WHERE clause evaluates to true
					result.page[i] &&

					// * The row's creation transaction ID is a committed transaction and is less than the current
					//   transaction counter.
					(
						selectedDatabase.transactionIsCommitted(tp.createTransactionID[i]) &&
						tp.createTransactionID[i] < transactionID
					) &&
					
					// * The row lacks an expiration transaction ID or its expiration transaction ID was in process at
					//   query start.
					(
						tp.expireTransactionID[i] == TransactionPage.EXPIRE_NEVER ||
						inTransactionIDs(tp.expireTransactionID[i])
					)
				)
						tuples.add(new Tuple(pageID * Page.TUPLES_PER_PAGE + i, tupleSize));
			}
		}

		cost.realMillis = Calendar.getInstance().getTimeInMillis() - start;
	}

	@Override
	public PlanItemCost getPlanItemCost() {
		return cost;
	}

}
