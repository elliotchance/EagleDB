package net.eagledb.bench;

import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import net.eagledb.server.planner.*;
import java.util.*;

public class PlanTest {

	int tuples = 100;

	Table table = new Table("mytable");

	public static void main(String[] args) {
		new PlanTest();
	}

	public void insertRecords() {
		Tuple tuple = new Tuple(table.getFields().size());
		for(int j = 0; j < tuples; ++j) {
			tuple.set(0, j);
			tuple.set(1, (float) Math.sqrt(j));
			table.addTuple(tuple);
		}
	}

	public PlanTest() {
		// create a memory based table
		table.addField(new Field("id", net.eagledb.server.sql.type.Integer.class));
		table.addField(new Field("value", net.eagledb.server.sql.type.Real.class));

		// insert some rows
		insertRecords();

		System.out.println(table.transactionPageTail.getTotalTuples());

		// create the plan
		Plan p = new Plan();
		p.plan.add(new FullTableScan(table, 0, PageScanAction.OPERATOR_EQUAL, 50));
		System.out.println(p);
		p.execute();

		// filter
		/*long start = Calendar.getInstance().getTimeInMillis();
		int count = filter();
		long time = Calendar.getInstance().getTimeInMillis() - start;

		// stats
		System.out.println("Table rows = " + table.getTotalTuples());
		System.out.println("Count = " + count);
		System.out.println("Time = " + time + " ms");*/
	}

}
