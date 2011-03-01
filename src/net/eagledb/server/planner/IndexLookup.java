package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.storage.Index;
import net.eagledb.server.storage.Tuple;

public class IndexLookup implements PlanItem {

	private Index index = null;

	public IndexLookup(Index index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "IndexLookup";
	}

	public void execute(ArrayList<Tuple> tuples, long transactionID) {
		// perform the lookup
		ArrayList<Integer> tupleIDs = index.page.lookup(IndexLookupOperation.EQUAL, 16);

		// translate the tuple ID into a virtual table

		System.out.print("$$$ ");
		for(int tupleID : tupleIDs)
			System.out.print(tupleID + " ");
		System.out.println();
	}

	public PlanItemCost getPlanItemCost() {
		return new PlanItemCost();
	}

}
