package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import java.util.*;

public class FullTableScan implements PlanItem {

	public Table table;

	public PageScanAction action;

	public Object value;

	public int fieldID;

	private ArrayList<Tuple> tuples;

	public FullTableScan(Table table, int fieldID, PageScanAction action, Object value) {
		this.table = table;
		this.fieldID = fieldID;
		this.action = action;
		this.value = value;
	}

	@Override
	public String toString() {
		String line = "FullTableScan ( " + table.getName() + "." + table.getAttributes().get(fieldID).getName();
		
		if(action == PageScanAction.OPERATOR_ALL)
			line += " : all rows";
		else {
			if(action == PageScanAction.OPERATOR_EQUAL)
				line += " = ";
			else
				line += "?";
			line += value;
		}
		line += " )";
		
		return line;
	}

	public void execute(ArrayList<Tuple> tuples) {
		TransactionPage tp = table.transactionPageHead;
		table.pageHeads.get(fieldID).scan(tp, tuples, action, value);
	}

}
