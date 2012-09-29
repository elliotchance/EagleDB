package net.eagledb.server.planner;

import java.util.ArrayList;
import java.util.Calendar;
import net.eagledb.server.planner.orderby.OrderByDouble;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.Tuple;

public class OrderBy implements PlanItem {

	protected ArrayList<Tuple> tuples;

	protected Table table;

	protected ArrayList<OrderByAttribute> columns = new ArrayList<OrderByAttribute>();

	public PlanItemCost cost = new PlanItemCost();

	public OrderBy(Table table, ArrayList<OrderByAttribute> columns) {
		this.table = table;
		this.columns = columns;
		estimateCost();
	}

	private void estimateCost() {
		/*int totalBuffers = 0;
		for(int i = 0; i < operations.length; ++i) {
			if(operations[i].getMaxBuffer() > totalBuffers)
				totalBuffers += operations[i].getMaxBuffer();
		}

		// assume a full table scan reads every page, multiple that by how much work is done on each page
		cost.estimateMinimumTimerons = cost.estimateMaximumTimerons = table.getTotalPages() * totalBuffers;*/
	}

	@Override
	public String toString() {
		String r = "Sort (";
		for(OrderByAttribute ex : columns) {
			r += " " + ex.toString();
		}
		return r + " )";
	}

	public void execute(int pageTuples, ArrayList<Tuple> tuples, long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();

		// extract the attribute
		OrderByDouble ob = new OrderByDouble(tuples.size());
		int location = columns.get(0).position;
		for(Tuple tuple : tuples) {
			ob.push(tuple.tupleID, Double.valueOf(tuple.get(location).toString()));
		}

		// perform the sort
		if(columns.get(0).isAsc) {
			ob.sortAscending();
		}
		else {
			ob.sortDescending();
		}

		// map the sorted tuples back to the table
		ArrayList<Tuple> newtuples = new ArrayList<Tuple>();
		for(int i = 0; i < ob.size(); ++i) {
			newtuples.add(tuples.get(ob.tupleIDs[i]));
		}
		tuples.clear();
		for(int i = 0; i < ob.size(); ++i) {
			tuples.add(newtuples.get(i));
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
		// this method is not used with DELETE
	}

}
