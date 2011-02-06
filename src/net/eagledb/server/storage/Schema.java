package net.eagledb.server.storage;

import java.util.ArrayList;

public class Schema {

	private String name;

	private ArrayList<Table> tables;

	public Schema(String name) {
		this.name = name;
		tables = new ArrayList<Table>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public Table getTable(String tableName) {
		for(Table table : tables) {
			if(table.name.equals(tableName))
				return table;
		}
		return null;
	}

	public synchronized void createTable(Table table) {
		tables.add(table);
	}

}
