package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.page.Page;

public class IndexScan extends FullTableScan {

	public IndexScan(ClientConnection conn, Table table, int tupleSize, String clause, PageOperation[] operations,
		ArrayList<Page> buffers, int limitOffset, int limit) {
		super(conn, table, tupleSize, clause, operations, buffers, limitOffset, limit);
	}

	@Override
	public String toString() {
		return "IndexScan ( " + clause + " )";
	}

}
