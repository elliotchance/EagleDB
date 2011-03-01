package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.storage.Attribute;
import net.eagledb.server.storage.Index;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.Tuple;

public class IndexLookup implements PlanItem {

	private Index index = null;
	
	private Table inputTable;

	public Table virtualTable = new Table("VIRTUAL", null);

	public IndexLookup(Table inputTable, Index index) {
		this.inputTable = inputTable;
		this.index = index;
	}

	@Override
	public String toString() {
		return "IndexLookup ( " + index.getDefinition() + " )";
	}

	public void execute(ArrayList<Tuple> tuples, long transactionID) {
		// perform the lookup
		ArrayList<Integer> tupleIDs = index.page.lookup(IndexLookupOperation.EQUAL, 16);

		// translate the tuple ID into a virtual table
		inputTable.createVirtualTable(virtualTable, 0);
	}

	public PlanItemCost getPlanItemCost() {
		return new PlanItemCost();
	}

}
