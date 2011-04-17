package net.eagledb.server.planner;

import java.util.ArrayList;
import net.eagledb.server.storage.Tuple;

public interface PlanItem {

	@Override
	public String toString();

	public void execute(int pageTuples, ArrayList<Tuple> tuples, long transactionID);

	public void executeDelete(long transactionID);

	public PlanItemCost getPlanItemCost();

}
