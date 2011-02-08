package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import java.util.*;
import net.eagledb.server.sql.type.*;

public class FetchAttributes implements PlanItem {

	private Table table;
	
	private int[] destinations;

	private int[] sources;

	private Class<? extends SQLType>[] types;
	
	public FetchAttributes(Table table, int[] sources, int[] destinations, Class<? extends SQLType>[] types) {
		this.table = table;
		this.sources = sources;
		this.destinations = destinations;
		this.types = types;
	}

	@Override
	public String toString() {
		String line = "FetchAttributes ( ";
		for(int i = 0; i < destinations.length; ++i) {
			if(i > 0)
				line += ", ";
			line += table.getAttributes().get(sources[i]).getName() + /* types[i].getSimpleName() + */ " => " +
				destinations[i];
		}
		return line + " )";
	}

	public void execute(ArrayList<Tuple> tuples) {
		for(int i = 0; i < destinations.length; ++i) {
			if(types[sources[i]] == net.eagledb.server.sql.type.Integer.class) {
				for(Tuple tuple : tuples) {
					IntPage page = (IntPage) table.getPage(sources[i], tuple.tupleID / Page.TUPLES_PER_PAGE);
					tuple.set(destinations[i], page.page[tuple.tupleID % Page.TUPLES_PER_PAGE]);
				}
			}
			else if(types[sources[i]] == net.eagledb.server.sql.type.Real.class) {
				for(Tuple tuple : tuples) {
					RealPage page = (RealPage) table.getPage(sources[i], tuple.tupleID / Page.TUPLES_PER_PAGE);
					tuple.set(destinations[i], page.page[tuple.tupleID % Page.TUPLES_PER_PAGE]);
				}
			}
		}
	}

}
