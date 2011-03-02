package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.page.Page;

public class IndexScan extends FullTableScan {

	public IndexScan(Database selectedDatabase, Table table, int tupleSize, String clause, PageOperation[] operations,
		ArrayList<Page> buffers, int limitOffset, int limit) {
		super(selectedDatabase, table, tupleSize, clause, operations, buffers, limitOffset, limit);
	}

	@Override
	public String toString() {
		return "IndexScan ( " + clause + " )";
	}

}
