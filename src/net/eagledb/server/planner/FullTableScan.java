package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import java.util.*;

public class FullTableScan implements PlanItem {

	private Table table;

	private PageOperation[] operations;

	private ArrayList<Tuple> tuples;

	private int tupleSize;

	private String clause;

	private PlanItemCost cost = new PlanItemCost();

	public FullTableScan(Table table, int tupleSize, String clause, PageOperation[] operations) {
		this.table = table;
		this.operations = operations;
		this.tupleSize = tupleSize;
		this.clause = clause;
		estimateCost();
	}

	private void estimateCost() {
		int totalBuffers = 0;
		for(int i = 0; i < operations.length; ++i) {
			if(operations[i].getMaxBuffer() > totalBuffers)
				totalBuffers += operations[i].getMaxBuffer();
		}

		// assume a full table scan reads every page, multiple that by how much work is done on each page
		cost.estimateMinimumTimerons = cost.estimateMaximumTimerons = table.getTotalPages() * totalBuffers;
	}

	@Override
	public String toString() {
		return "FullTableScan ( " + table.getName() + ": " + clause + " )";
	}

	public void execute(ArrayList<Tuple> tuples) {
		long start = Calendar.getInstance().getTimeInMillis();

		// calculate the number of buffers we need
		int totalBuffers = 0;
		for(int i = 0; i < operations.length; ++i) {
			if(operations[i].getMaxBuffer() > totalBuffers)
				totalBuffers = operations[i].getMaxBuffer();
		}

		// create buffers
		boolean[][] buffer = new boolean[totalBuffers + 1][Page.TUPLES_PER_PAGE];

		// run operations
		for(int pageID = 0; pageID < table.getTotalPages(); ++pageID) {
			TransactionPage tp = table.getTransactionPage(pageID, cost);
			for(PageOperation operation : operations)
				operation.run(tp, table.getPage(operation.fieldID, pageID, cost), buffer);

			for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
				if(buffer[totalBuffers][i])
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
