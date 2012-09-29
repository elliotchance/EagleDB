package net.eagledb.server.planner;

import java.util.*;
import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.BooleanPage;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;
import net.eagledb.server.storage.page.VarCharPage;

public class FetchAttributes implements PlanItem {

	protected Table table;
	
	protected int[] destinations;

	protected ArrayList<net.eagledb.server.planner.Expression> sources;

	protected PlanItemCost cost = new PlanItemCost();
	
	public FetchAttributes(Table table, ArrayList<net.eagledb.server.planner.Expression> sources, int[] destinations) {
		this.table = table;
		this.sources = sources;
		this.destinations = destinations;
		estimateCost();
	}

	private void estimateCost() {
		// if no tuples were found in the previous steps then the minimum cost is zero
		cost.estimateMinimumTimerons = 0;

		// the maximum is more tricky since it could be up to the number of records that can come from the previous
		// steps, for now lets call that 10
		cost.estimateMaximumTimerons = 10;
	}

	@Override
	public String toString() {
		String line = "Fetch Attributes ( ";
		for(int i = 0; i < sources.size(); ++i) {
			if(i > 0) {
				line += ", ";
			}
			line += sources.get(i) + " -> " + destinations[i];
		}
		return line + " )";
	}

	public void execute(int pageTuples, ArrayList<Tuple> tuples, long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();

		try {
			for(int i = 0; i < destinations.length; ++i) {
				net.eagledb.server.planner.Expression source = sources.get(i);
				PageOperation[] ops = source.parse(false);
				FullTableScan fts = new FullTableScan(null, table, table.getAttributes().length, null, ops,
					source.buffers, 0, tuples.size());

				for(PageOperation operation : ops) {
					operation.run(pageTuples, fts);
				}

				Page basePage = source.buffers.get(source.buffers.size() - 1);
				if(basePage instanceof IntPage) {
					IntPage page = (IntPage) basePage;
					for(Tuple tuple : tuples) {
						tuple.set(destinations[i], page.page[tuple.tupleID]);
					}
				}
				else if(basePage instanceof DoublePage) {
					DoublePage page = (DoublePage) basePage;
					for(Tuple tuple : tuples) {
						tuple.set(destinations[i], page.page[tuple.tupleID]);
					}
				}
				else if(basePage instanceof BooleanPage) {
					BooleanPage page = (BooleanPage) basePage;
					for(Tuple tuple : tuples) {
						tuple.set(destinations[i], page.page[tuple.tupleID]);
					}
				}
				else if(basePage instanceof VarCharPage) {
					VarCharPage page = (VarCharPage) basePage;
					for(Tuple tuple : tuples) {
						tuple.set(destinations[i], page.page[tuple.tupleID]);
					}
				}
				else {
					throw new Exception("Cannot cast " + basePage.getClass().getSimpleName());
				}
			}
		}
		catch(ExpressionException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		cost.realMillis = Calendar.getInstance().getTimeInMillis() - start;
	}

	@Override
	public PlanItemCost getPlanItemCost() {
		return cost;
	}

	public void executeUpdate(int[] columnID, net.eagledb.server.planner.Expression[] ex, long transactionID) {
		// do nothing
	}

	public void executeDelete(long transactionID) {
		// do nothing
	}

}
