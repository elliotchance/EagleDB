package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import java.util.*;
import net.eagledb.server.sql.type.*;

public class FetchAttributes implements PlanItem {

	private Table table;
	
	private int[] destinations;

	private int[] sources;

	private Class<? extends SQLType>[] types;

	private PlanItemCost cost = new PlanItemCost();
	
	public FetchAttributes(Table table, int[] sources, int[] destinations, Class<? extends SQLType>[] types) {
		this.table = table;
		this.sources = sources;
		this.destinations = destinations;
		this.types = types;
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
			line += table.getAttributes().get(sources[i]).getName() + /* types[i].getSimpleName() + */ " => " +
				destinations[i];
		}
		return line + " )";
	}

	public void execute(ArrayList<Tuple> tuples) {
		long start = Calendar.getInstance().getTimeInMillis();

		for(int i = 0; i < destinations.length; ++i) {
			if(types[sources[i]] == net.eagledb.server.sql.type.Integer.class) {
				for(Tuple tuple : tuples) {
					IntPage page = (IntPage) table.getPage(sources[i], tuple.tupleID / Page.TUPLES_PER_PAGE);
					tuple.set(destinations[i], page.page[tuple.tupleID % Page.TUPLES_PER_PAGE]);
				}
			}
			else if(types[sources[i]] == net.eagledb.server.sql.type.Real.class) {
				for(Tuple tuple : tuples) {
					RealPage page = (RealPage) table.getPage(sources[i], tuple.tupleID / Page.TUPLES_PER_PAGE);
					tuple.set(destinations[i], page.page[tuple.tupleID % Page.TUPLES_PER_PAGE]);
				}
			}
		}

		cost.realMillis = Calendar.getInstance().getTimeInMillis() - start;
	}

	@Override
	public PlanItemCost getPlanItemCost() {
		return cost;
	}

}
