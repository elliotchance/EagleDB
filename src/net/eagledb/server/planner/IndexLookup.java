package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.storage.Attribute;
import net.eagledb.server.storage.Index;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.Tuple;

public class IndexLookup implements PlanItem {

	protected Index index = null;
	
	protected Table inputTable;

	public Table virtualTable = new Table("VIRTUAL", null);

	protected IndexLookupOperation op;

	protected Object value;

	public IndexLookup(Table inputTable, Index index, IndexLookupOperation op, Object value) {
		this.inputTable = inputTable;
		this.index = index;
		this.op = op;
		this.value = value;
	}

	@Override
	public String toString() {
		return "IndexLookup ( " + index.getDefinition() + " )";
	}

	public void execute(ArrayList<Tuple> tuples, long transactionID) {
		// perform the lookup
		ArrayList<Integer> tupleIDs = index.page.lookup(op, Integer.valueOf(value.toString()));

		// translate the tuple ID into a virtual table
		int pageID = (int) Math.floor(tupleIDs.get(0) / 1000);
		inputTable.createVirtualTable(virtualTable, pageID);
	}

	public PlanItemCost getPlanItemCost() {
		return new PlanItemCost();
	}

	public void executeDelete(long transactionID) {
		// do nothing
	}

}
