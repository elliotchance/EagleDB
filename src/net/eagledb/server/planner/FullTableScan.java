package net.eagledb.server.planner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.storage.Attribute;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.Tuple;
import net.eagledb.server.storage.page.BooleanPage;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;
import net.eagledb.server.storage.page.TransactionPage;
import net.eagledb.server.storage.page.VarCharPage;

public class FullTableScan implements PlanItem {

	public Table table;

	protected ClientConnection conn;

	public PageOperation[] operations;

	protected ArrayList<Tuple> tuples;

	protected int tupleSize;

	protected String clause;

	public PlanItemCost cost = new PlanItemCost();

	public ArrayList<Page> buffers;

	public int pageID;

	/**
	 * Initialise with execute(). This array holds all the transaction IDs currently in progress before the full table
	 * scan starts.
	 */
	private long[] transactionIDs;

	protected int limit, limitOffset, skipped = 0;

	public FullTableScan(ClientConnection conn, Table table, int tupleSize, String clause, PageOperation[] operations,
		ArrayList<Page> buffers, int limitOffset, int limit) {
		this.table = table;
		this.operations = operations;
		this.tupleSize = tupleSize;
		this.clause = clause;
		this.buffers = buffers;
		this.limitOffset = limitOffset;
		this.limit = limit;
		this.conn = conn;
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
		// resolve table name
		String tableName = table.getName();
		if(conn.getTemporaryTableByInternalName(tableName) != null)
			tableName = conn.getTemporaryTableByInternalName(tableName).name;
		
		return "Full Table Scan \"" + tableName + "\" WHERE (" + clause + ")";
	}

	private boolean inTransactionIDs(long XID) {
		if(transactionIDs == null)
			return false;
		
		for(int i = 0; i < transactionIDs.length; ++i) {
			if(transactionIDs[i] == XID)
				return true;
		}
		return false;
	}

	protected boolean rowIsVisible(long transactionID, TransactionPage tp, int i) {
		// Display all the row versions that match the following criteria:
		return
			// * The row's creation transaction ID is a committed transaction and is less than the current
			//   transaction counter.
			(
				conn.getSelectedDatabase().transactionIsCommitted(tp.createTransactionID[i]) &&
				tp.createTransactionID[i] < transactionID
			) &&

			// * The row lacks an expiration transaction ID or its expiration transaction ID was in process at
			//   query start.
			(
				tp.expireTransactionID[i] == TransactionPage.EXPIRE_NEVER ||
				inTransactionIDs(tp.expireTransactionID[i])
			);
	}

	public void execute(int pageTuples, ArrayList<Tuple> tuples, long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();

		// run operations
		for(pageID = 0; pageID < table.getTotalPages(); ++pageID) {
			for(PageOperation operation : operations)
				operation.run(pageTuples, this);

			TransactionPage tp = table.getTransactionPage(pageID, cost);
			BooleanPage result = (BooleanPage) buffers.get(buffers.size() - 1);
			for(int i = 0; i < pageTuples; ++i) {
				if(result.page[i] && rowIsVisible(transactionID, tp, i)) {
					// handle the limit
					if(skipped < limitOffset) {
						++skipped;
						continue;
					}
					if(tuples.size() >= limit)
						break;
					
					tuples.add(new Tuple(pageID * Page.TUPLES_PER_PAGE + i, tupleSize));
				}
			}
		}

		cost.realMillis = Calendar.getInstance().getTimeInMillis() - start;
	}

	@Override
	public PlanItemCost getPlanItemCost() {
		return cost;
	}

	public void executeUpdate(int[] columnID, net.eagledb.server.planner.Expression[] ex, long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis(), matched = 0;

		// run operations
		for(pageID = 0; pageID < table.getTotalPages(); ++pageID) {
			for(PageOperation operation : operations)
				operation.run(Page.TUPLES_PER_PAGE, this);

			TransactionPage tp = table.getTransactionPage(pageID, cost);
			BooleanPage result = (BooleanPage) buffers.get(buffers.size() - 1);
			Page[] pages = new Page[table.getAttributes().length];
			for(int i = 0; i < table.getAttributes().length; ++i)
				pages[i] = table.getPage(i, pageID, cost);
			
			for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
				if(result.page[i] && rowIsVisible(transactionID, tp, i)) {
					// handle the limit
					if(skipped < limitOffset) {
						++skipped;
						continue;
					}
					if(matched >= limit)
						break;

					++matched;
					
					// expire this tuple
					tp.expireTransactionID[i] = transactionID;
					
					// duplicate the tuple
					Tuple tuple = new Tuple(table.getAttributes().length);
					int j = 0;
					for(Attribute attr : table.getAttributes()) {
						// put the data into the tuple
						if(pages[j] instanceof DoublePage)
							tuple.set(j, ((DoublePage) pages[j]).page[i]);
						else if(pages[j] instanceof IntPage)
							tuple.set(j, ((IntPage) pages[j]).page[i]);
						else
							System.err.println("UPDATE cannot cast " + pages[j].getClass().getSimpleName());
						++j;
					}
					
					// update the attributes
					try {
						for(j = 0; j < columnID.length; ++j) {
							net.eagledb.server.planner.Expression source = ex[j];
							PageOperation[] ops = source.parse(false);
							FullTableScan fts = new FullTableScan(null, table, table.getAttributes().length, null, ops,
								ex[j].buffers, 0, 1);

							for(PageOperation operation : ops)
								operation.run(1, fts);

							Page basePage = source.buffers.get(source.buffers.size() - 1);
							if(basePage instanceof IntPage) {
								IntPage page = (IntPage) basePage;
								tuple.set(columnID[j], page.page[0]);
							}
							else if(basePage instanceof DoublePage) {
								DoublePage page = (DoublePage) basePage;
								tuple.set(columnID[j], page.page[0]);
							}
							else if(basePage instanceof BooleanPage) {
								BooleanPage page = (BooleanPage) basePage;
								tuple.set(columnID[j], page.page[0]);
							}
							else if(basePage instanceof VarCharPage) {
								VarCharPage page = (VarCharPage) basePage;
								tuple.set(columnID[j], page.page[0]);
							}
							else
								System.err.println("Cannot cast " + basePage.getClass().getSimpleName());

							//System.out.println();
							//tuple.set(columnID[j], 1.0);
						}
					}
					catch (ExpressionException ex1) {
						Logger.getLogger(FullTableScan.class.getName()).log(Level.SEVERE, null, ex1);
					}

					// add the newly built tuple
					table.addTuple(tuple, transactionID);
				}
			}
		}

		cost.realMillis = Calendar.getInstance().getTimeInMillis() - start;
	}

	public void executeDelete(long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis(), matched = 0;

		// run operations
		for(pageID = 0; pageID < table.getTotalPages(); ++pageID) {
			for(PageOperation operation : operations)
				operation.run(Page.TUPLES_PER_PAGE, this);

			TransactionPage tp = table.getTransactionPage(pageID, cost);
			BooleanPage result = (BooleanPage) buffers.get(buffers.size() - 1);
			for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
				if(result.page[i] && rowIsVisible(transactionID, tp, i)) {
					// handle the limit
					if(skipped < limitOffset) {
						++skipped;
						continue;
					}
					if(matched >= limit)
						break;

					++matched;
					tp.expireTransactionID[i] = transactionID;
				}
			}
		}

		cost.realMillis = Calendar.getInstance().getTimeInMillis() - start;
	}

}
