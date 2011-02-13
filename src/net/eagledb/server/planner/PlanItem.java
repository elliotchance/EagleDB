package net.eagledb.server.planner;

import java.util.*;
import net.eagledb.server.storage.*;

public interface PlanItem {

	@Override
	public String toString();

	public void execute(ArrayList<Tuple> tuples);

	public PlanItemCost getPlanItemCost();

}
