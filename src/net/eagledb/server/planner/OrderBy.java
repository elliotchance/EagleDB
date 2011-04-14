package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import java.util.*;
import net.eagledb.server.planner.orderby.OrderByDouble;
import net.sf.jsqlparser.statement.select.OrderByElement;

public class OrderBy implements PlanItem {

	protected ArrayList<Tuple> tuples;

	protected Table table;

	protected ArrayList<OrderByElement> expressions = new ArrayList<OrderByElement>();

	public PlanItemCost cost = new PlanItemCost();

	public OrderBy(Table table, List expressions) {
		this.table = table;
		for(Object ex : expressions)
			this.expressions.add((OrderByElement) ex);
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
		String r = "OrderBy (";
		for(OrderByElement ex : expressions)
			r += " " + ex.toString();
		return r + " )";
	}

	public void execute(ArrayList<Tuple> tuples, long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();

		// extract the attribute
		OrderByDouble ob = new OrderByDouble(tuples.size());
		int location = 0; //table.getAttributeLocation(expressions.get(0).getColumnReference().toString());
		for(Tuple tuple : tuples)
			ob.push(tuple.tupleID, Double.valueOf(tuple.get(location).toString()));

		// perform the sort
		if(expressions.get(0).isAsc())
			ob.sortAscending();
		else
			ob.sortDescending();

		// map the sorted tuples back to the table
		ArrayList<Tuple> newtuples = new ArrayList<Tuple>();
		for(int i = 0; i < ob.size(); ++i)
			newtuples.add(tuples.get(ob.tupleIDs[i]));
		tuples.clear();
		for(int i = 0; i < ob.size(); ++i)
			tuples.add(newtuples.get(i));

		cost.realMillis = Calendar.getInstance().getTimeInMillis() - start;
	}

	@Override
	public PlanItemCost getPlanItemCost() {
		return cost;
	}

	public void executeDelete(long transactionID) {
		// this method is not used with DELETE
	}

}
