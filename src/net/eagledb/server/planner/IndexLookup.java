package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.storage.Index;
import net.eagledb.server.storage.Tuple;
import net.eagledb.server.storage.index.SearchResult;

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
		SearchResult result = index.page.searchObj(2);
		System.out.println(result);
	}

	public PlanItemCost getPlanItemCost() {
		return new PlanItemCost();
	}

}
