package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.Tuple;
import net.eagledb.server.storage.page.Page;

public class IndexScan extends FullTableScan {

	public IndexScan(Database selectedDatabase, Table table, int tupleSize, String clause, PageOperation[] operations,
		ArrayList<Page> buffers) {
		super(selectedDatabase, table, tupleSize, clause, operations, buffers);
	}

	@Override
	public void execute(ArrayList<Tuple> tuples, long transactionID) {
		System.out.println("IndexScan: " + table.getTotalPages());
		super.execute(tuples, transactionID);
	}

	@Override
	public String toString() {
		return "IndexScan ( " + clause + " )";
	}

}
