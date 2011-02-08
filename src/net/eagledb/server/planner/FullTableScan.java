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

	public FullTableScan(Table table, int tupleSize, String clause, PageOperation[] operations) {
		this.table = table;
		this.operations = operations;
		this.tupleSize = tupleSize;
		this.clause = clause;
	}

	@Override
	public String toString() {
		return "FullTableScan ( " + table.getName() + ": " + clause + " )";
	}

	public void execute(ArrayList<Tuple> tuples) {
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
			TransactionPage tp = table.getTransactionPage(pageID);
			for(PageOperation operation : operations)
				operation.run(tp, table.getPage(operation.fieldID, pageID), buffer);

			for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
				if(buffer[totalBuffers][i])
					tuples.add(new Tuple(pageID * Page.TUPLES_PER_PAGE + i, tupleSize));
			}
		}
		//System.out.println(Arrays.toString(buffer[0]));
		//table.pageHeads.get(fieldID).scan(tp, tuples, action, value);
	}

}
