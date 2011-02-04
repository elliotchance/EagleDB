package net.eagledb.bench;

import net.eagledb.server.storage.page.TransactionPage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.*;
import java.util.Calendar;

public class PageFilter {

	int tuples = 1000000;

	Table table = new Table("mytable");

	public static void main(String[] args) {
		new PageFilter();
	}

	public void fillCache() {
		Tuple tuple = new Tuple(table.getFields().size());
		for(int j = 0; j < tuples; ++j) {
			tuple.set(0, (int) Math.sqrt(j));
			table.addTuple(tuple);
		}
	}

	public int filter() {
		int count = 0;
		for(int i = 0; i < table.getTotalPages(); ++i) {
			TransactionPage tp = table.getTransactionPage(i);
			IntPage focus = (IntPage) table.getPage(0, i);
			for(int j = 0; j < 1000; ++j) {
				if(tp.transactionID[j] > 0 && focus.page[j] > 300)
					++count;
			}
		}
		return count;
	}

	public PageFilter() {
		// create a memory based table
		table.addField(new Field("id", net.eagledb.server.sql.type.Integer.class));

		// create a page
		for(int i = 0; i < tuples / 1000; ++i)
			table.addPage();

		fillCache();

		// filter
		long start = Calendar.getInstance().getTimeInMillis();
		int count = filter();
		long time = Calendar.getInstance().getTimeInMillis() - start;

		// stats
		System.out.println("Table rows = " + table.getTotalTuples());
		System.out.println("Count = " + count);
		System.out.println("Time = " + time + " ms");
	}

}
