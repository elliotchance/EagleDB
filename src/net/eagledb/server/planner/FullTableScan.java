package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import java.util.*;

public class FullTableScan implements PlanItem {

	public Table table;

	public PageScanAction action;

	public Object value;

	public int fieldID;

	public FullTableScan(Table table, int fieldID, PageScanAction action, Object value) {
		this.table = table;
		this.fieldID = fieldID;
		this.action = action;
		this.value = value;
	}

	@Override
	public String toString() {
		return "FullTableScan (" + table + ", " + action + ", " + value + ")";
	}

	public void execute() {
		TransactionPage tp = table.transactionPageHead;
		int[] transactionIDs = table.pageHeads.get(fieldID).scan(tp, action, value);
		System.out.println(Arrays.toString(transactionIDs));
	}

}
