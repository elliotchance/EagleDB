package net.eagledb.server.storage;

import java.util.ArrayList;

public class Database {

	/**
	 * Database name.
	 */
	private String name;

	/**
	 * The schemas in this database.
	 */
	private ArrayList<Schema> schemas;

	/**
	 * The most current transaction ID.
	 */
	private long transactionID = 1;

	/**
	 * Transaction ID that are currently in progress (not committed or rolled back.)
	 */
	private ArrayList<Long> transactionsInProgress = new ArrayList<Long>();

	private ArrayList<Index> indexes = new ArrayList<Index>();

	/**
	 * Initialise a database with a name.
	 * @param dbName The database name.
	 */
	public Database(String dbName) {
		name = dbName;
		schemas = new ArrayList<Schema>();
	}

	/**
	 * Add the transaction ID to currently running transactions.
	 * @return The transaction ID.
	 */
	public synchronized long beginTransaction() {
		transactionsInProgress.add(transactionID);
		return transactionID++;
	}

	/**
	 * Remove the transactionID from currently running transactions.
	 */
	public synchronized void commitTransaction(long XID) {
		transactionsInProgress.remove(new Long(XID));
	}

	/**
	 * Remove the transactionID from currently running transactions.
	 */
	public synchronized void rollbackTransaction(long XID) {
		transactionsInProgress.remove(new Long(XID));
	}

	/**
	 * Work out if a transactionID is committed.
	 * @param transactionID The transactionID to test.
	 * @return true if the transaction has been committed, otherwise false.
	 */
	public boolean transactionIsCommitted(long XID) {
		return !transactionsInProgress.contains(new Long(XID));
	}

	/**
	 * Get a schema by name.
	 * @param schemaName The schema name.
	 * @return The Schema object if it exists, otherwise null is returned.
	 */
	public Schema getSchema(String schemaName) {
		for(Schema schema : schemas) {
			if(schema.getName().equals(schemaName))
				return schema;
		}
		return null;
	}

	/**
	 * Get the database name. Database names are read only so the only way to change a database name is by recreating
	 * the Database object.
	 * @return The database name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Add a new Schema to the database. Schemas are not checked to be unique by name.
	 * @param schema Schema object.
	 */
	public synchronized void addSchema(Schema schema) {
		schemas.add(schema);
	}

	/**
	 * Get all schemas.
	 * @return All schema objects.
	 */
	public ArrayList<Schema> getSchemas() {
		return schemas;
	}

	/**
	 * Get the current transaction ID.
	 */
	public long getCurrentTransactionID() {
		return transactionID;
	}

	/**
	 * Get an array of all the transaction IDs currently in process.
	 */
	public synchronized long[] getTransactionIDs() {
		long[] r = new long[transactionsInProgress.size()];
		for(int i = 0; i < r.length; ++i)
			r[i] = transactionsInProgress.get(i);
		return r;
	}

	public synchronized void addIndex(Index index) {
		indexes.add(index);
	}

	public ArrayList<Index> getIndexes() {
		return indexes;
	}

}
