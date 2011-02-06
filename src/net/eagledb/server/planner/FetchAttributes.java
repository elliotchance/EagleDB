package net.eagledb.server.planner;

import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import java.util.*;
import net.eagledb.server.sql.type.*;

public class FetchAttributes implements PlanItem {

	private Table table;
	
	private int[] destinations;

	private Class<? extends SQLType>[] types;
	
	public FetchAttributes(Table table, int[] destinations, Class<? extends SQLType>[] types) {
		this.table = table;
		this.destinations = destinations;
		this.types = types;
	}

	@Override
	public String toString() {
		String line = "FetchAttributes (";
		//for(int i = 0; i < destinations.length; ++i)
		//	line += types[i] + " ";
		return line + " )";
	}

	public void execute(ArrayList<Tuple> tuples) {
		for(int i = 0; i < destinations.length; ++i) {
			if(types[i] == net.eagledb.server.sql.type.Integer.class) {
				for(Tuple tuple : tuples)
					tuple.set(destinations[i], ((IntPage) table.getPage(i, tuple.tupleID / Page.TUPLES_PER_PAGE)).page[tuple.tupleID % Page.TUPLES_PER_PAGE]);
			}
			else if(types[i] == net.eagledb.server.sql.type.Real.class) {
				for(Tuple tuple : tuples)
					tuple.set(destinations[i], ((RealPage) table.getPage(i, tuple.tupleID / Page.TUPLES_PER_PAGE)).page[tuple.tupleID % Page.TUPLES_PER_PAGE]);
			}
		}
	}

}
