package net.eagledb.server.planner;

import java.util.*;

public class Plan {

	public TreeSet<PlanItem> plan;

	public Plan() {
		plan = new TreeSet<PlanItem>();
	}

	@Override
	public String toString() {
		String r = "Execution Plan\n";
		for(PlanItem p : plan)
			r += "  " + p.toString() + "\n";
		return r;
	}

	public void execute() {
		for(PlanItem p : plan)
			p.execute();
	}

}
