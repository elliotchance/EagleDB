package net.eagledb.server.storage;

import java.util.ArrayList;

public class Schema {

	public String name;

	public ArrayList<Table> tables;

	public Schema(String name) {
		this.name = name;
		tables = new ArrayList<Table>();
	}

}
