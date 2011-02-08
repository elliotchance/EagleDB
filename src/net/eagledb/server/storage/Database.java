package net.eagledb.server.storage;

import java.util.ArrayList;

public class Database {

	private String name;

	private ArrayList<Schema> schemas;

	public Database(String dbName) {
		name = dbName;
		schemas = new ArrayList<Schema>();
	}

	public Schema getSchema(String schemaName) {
		for(Schema schema : schemas) {
			if(schema.getName().equals(schemaName))
				return schema;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public synchronized void addSchema(Schema schema) {
		schemas.add(schema);
	}

	public ArrayList<Schema> getSchemas() {
		return schemas;
	}

}
