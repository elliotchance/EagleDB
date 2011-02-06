package net.eagledb.server.planner;

import java.util.*;
import net.eagledb.server.storage.*;

public class Plan {

	public ArrayList<PlanItem> plan;

	private ArrayList<Tuple> tuples;

	public Plan() {
		plan = new ArrayList<PlanItem>();
	}

	@Override
	public String toString() {
		String r = "Execution Plan\n";
		for(PlanItem p : plan)
			r += "  " + p.toString() + "\n";
		return r;
	}

	public void execute() {
		tuples = new ArrayList<Tuple>();
		for(PlanItem p : plan)
			p.execute(tuples);
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

}
