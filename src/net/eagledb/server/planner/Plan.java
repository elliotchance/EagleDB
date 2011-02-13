package net.eagledb.server.planner;

import java.util.*;
import net.eagledb.server.storage.*;

public class Plan {

	private ArrayList<PlanItem> plan;

	private ArrayList<Tuple> tuples;

	private PlanStatistics statistics = null;

	private boolean hasExecuted = false;

	public Plan() {
		plan = new ArrayList<PlanItem>();
		statistics = new PlanStatistics();
	}

	@Override
	public String toString() {
		String r = "Execution Plan\n";
		String[] lines = getExplainLines();
		for(String line : lines)
			r += "  " + line + "\n";
		return r;
	}

	public String[] getExplainLines() {
		String[] lines = new String[plan.size() + 1];
		
		int i = 0, totalMinTimerons = 0, totalMaxTimerons = 0, totalRealMillis = 0;
		for(PlanItem p : plan) {
			PlanItemCost cost = p.getPlanItemCost();
			totalMinTimerons += cost.estimateMinimumTimerons;
			totalMaxTimerons += cost.estimateMaximumTimerons;

			String line = "";
			if(cost.estimateMinimumTimerons != cost.estimateMaximumTimerons)
				line = p.toString() + " => " + cost.estimateMinimumTimerons + ".." +
					cost.estimateMaximumTimerons + " timerons";
			else
				line = p.toString() + " => " + cost.estimateMinimumTimerons + " timerons";

			if(hasExecuted) {
				line += ", " + cost.realMillis + " ms";
				totalRealMillis += cost.realMillis;
			}
			lines[i++] = line;
		}

		// total line
		lines[i] = "Total: " + totalMinTimerons + ".." + totalMaxTimerons + " timerons";
		if(hasExecuted)
			lines[i] += ", " + totalRealMillis + " ms";

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

	public void execute() {
		long start = Calendar.getInstance().getTimeInMillis();
		tuples = new ArrayList<Tuple>();
		for(PlanItem p : plan)
			p.execute(tuples);

		statistics.executionTimeMillis = Calendar.getInstance().getTimeInMillis() - start;
		hasExecuted = true;
	}

	/**
	 * @return the tuples
	 */
	public Tuple[] getTuples() {
		Tuple[] ts = new Tuple[tuples.size()];
		int i = 0;
		for(Tuple t : tuples)
			ts[i++] = t;
		return ts;
	}

	public PlanStatistics getPlanStatistics() {
		return statistics;
	}

	public void addPlanItem(PlanItem item) {
		plan.add(item);
	}

}
