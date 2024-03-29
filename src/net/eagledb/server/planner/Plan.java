package net.eagledb.server.planner;

import java.util.ArrayList;
import java.util.Calendar;
import net.eagledb.server.storage.Tuple;
import net.eagledb.server.storage.page.Page;

public class Plan {

	protected ArrayList<PlanItem> plan;

	protected ArrayList<Tuple> tuples;

	protected PlanStatistics statistics = null;

	protected boolean hasExecuted = false;

	public int pageTuples = Page.TUPLES_PER_PAGE;

	public Plan() {
		plan = new ArrayList<PlanItem>();
		statistics = new PlanStatistics();
	}

	@Override
	public String toString() {
		String r = "Execution Plan\n";
		String[] lines = getExplainLines();
		for(String line : lines) {
			r += "  " + line + "\n";
		}
		return r;
	}

	public String[] getExplainLines() {
		String[] lines = new String[plan.size() + 1];
		
		int i = 0;
		PlanItemCost totalCost = new PlanItemCost();
		for(PlanItem p : plan) {
			PlanItemCost cost = p.getPlanItemCost();
			totalCost.estimateMinimumTimerons += cost.estimateMinimumTimerons;
			totalCost.estimateMaximumTimerons += cost.estimateMaximumTimerons;

			String line = "";
			if(cost.estimateMinimumTimerons != cost.estimateMaximumTimerons) {
				line = p.toString() + " => " + cost.estimateMinimumTimerons + ".." +
					cost.estimateMaximumTimerons + " timerons";
			}
			else {
				line = p.toString() + " => " + cost.estimateMinimumTimerons + " timerons";
			}

			if(hasExecuted) {
				line += ", " + cost.realMillis + " ms";
				totalCost.realMillis += cost.realMillis;
				totalCost.pagesReadFromCache += cost.pagesReadFromCache;
				totalCost.pagesReadFromDisk += cost.pagesReadFromDisk;
			}
			lines[i++] = line;
		}

		// total line
		lines[i] = "Total: " + totalCost.estimateMinimumTimerons + ".." + totalCost.estimateMaximumTimerons +
			" timerons";
		if(hasExecuted) {
			lines[i] += ", " + tuples.size() + " tuples, " + totalCost.realMillis + " ms, " +
				totalCost.pagesReadFromCache + " RAM pages, " + totalCost.pagesReadFromDisk + " disk pages";
		}

		return lines;
	}

	public Tuple[] getExplainTuples() {
		String[] lines = getExplainLines();
		Tuple[] ts = new Tuple[lines.length];
		int i = 0;
		for(String line : lines) {
			ts[i] = new Tuple(1);
			ts[i].set(0, line);
			++i;
		}
		return ts;
	}

	public void executeUpdate(int[] columnID, net.eagledb.server.planner.Expression[] ex, long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();
		for(PlanItem p : plan) {
			p.executeUpdate(columnID, ex, transactionID);
		}

		statistics.executionTimeMillis = Calendar.getInstance().getTimeInMillis() - start;
		hasExecuted = true;
	}

	public void executeDelete(long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();
		for(PlanItem p : plan) {
			p.executeDelete(transactionID);
		}

		statistics.executionTimeMillis = Calendar.getInstance().getTimeInMillis() - start;
		hasExecuted = true;
	}

	public void execute(long transactionID) {
		long start = Calendar.getInstance().getTimeInMillis();
		tuples = new ArrayList<Tuple>();
		for(PlanItem p : plan) {
			p.execute(pageTuples, tuples, transactionID);
		}

		statistics.executionTimeMillis = Calendar.getInstance().getTimeInMillis() - start;
		hasExecuted = true;
	}

	/**
	 * @return the tuples
	 */
	public Tuple[] getTuples() {
		Tuple[] ts = new Tuple[tuples.size()];
		int i = 0;
		for(Tuple t : tuples) {
			ts[i++] = t;
		}
		return ts;
	}

	public PlanStatistics getPlanStatistics() {
		return statistics;
	}

	public void addPlanItem(PlanItem item) {
		plan.add(item);
	}

}
