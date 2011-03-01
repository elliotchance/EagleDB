package net.eagledb.server.storage;

import java.util.ArrayList;
import net.eagledb.server.storage.index.IntIndexPage;

public class Index {

	public String name;

	public String table;

	public ArrayList<String> columns;

	public IntIndexPage page;

	public String getDefinition() {
		return table + "(" + columns.get(0) + ")";
	}

	@Override
	public String toString() {
		return "index " + name + " on " + table + "(" + columns.get(0) + ")";
	}

}
