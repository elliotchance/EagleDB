package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import java.util.*;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;

public class FetchAttributes implements PlanItem {

	private Table table;
	
	private int[] destinations;

	private ArrayList<net.eagledb.server.planner.Expression> sources;

	private PlanItemCost cost = new PlanItemCost();
	
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
		String line = "FetchAttributes ( ";
		for(int i = 0; i < destinations.length; ++i) {
			if(i > 0)
				line += ", ";
			//line += table.getAttributes()[sources[i]].getName() + /* types[i].getSimpleName() + */ " => " +
			//	destinations[i];
		}
		return line + " )";
	}

	public void execute(ArrayList<Tuple> tuples, long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();

		try {
			for(int i = 0; i < destinations.length; ++i) {
				net.eagledb.server.planner.Expression source = sources.get(i);
				PageOperation[] ops = source.parse(false);
				FullTableScan fts = new FullTableScan(null, table, table.getAttributes().length, null, ops,
					source.buffers, 0, tuples.size());

				for(PageOperation operation : ops) {
					operation.run(fts);
					System.out.println(operation);
				}

				System.out.println("BASE PAGE '" + source.toString() + "': " + java.util.Arrays.toString(ops));
				Page basePage = source.buffers.get(source.buffers.size() - 1);

				if(basePage instanceof IntPage) {
					IntPage page = (IntPage) basePage;
					for(Tuple tuple : tuples)
						tuple.set(destinations[i], page.page[tuple.tupleID]);
				}
				else if(basePage instanceof DoublePage) {
					DoublePage page = (DoublePage) basePage;
					for(Tuple tuple : tuples)
						tuple.set(destinations[i], page.page[tuple.tupleID]);
				}
				else
					throw new Exception("Cannot cast " + basePage.getClass().getSimpleName());
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

	public void executeDelete(long transactionID) {
		// do nothing
	}

}
