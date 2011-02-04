package net.eagledb.server.storage;

import java.util.ArrayList;

public class Database {

	public String name;

	public ArrayList<Schema> schemas;

	public Database(String dbName) {
		name = dbName;
	}

}
