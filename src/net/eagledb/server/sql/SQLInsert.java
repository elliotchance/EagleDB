package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.insert.Insert;
import net.eagledb.server.*;
import java.sql.*;
import net.eagledb.server.storage.*;
import java.util.*;
import net.eagledb.server.planner.*;

public class SQLInsert extends SQLAction {
	
	private Insert sql;

	public SQLInsert(Server server, ClientConnection conn, Insert sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// we must have a selected database
		Database selectedDatabase = conn.getSelectedDatabase();
		if(selectedDatabase == null)
			throw new SQLException("No database selected.");

		// check the users permission
		//if(!conn.getUser().canCreateTable)
		//	throw new SQLException("Permission denied. You must have the CREATE TABLE privilege.");

		// get schema
		Schema schema = selectedDatabase.getSchema("public");
		if(schema == null)
			throw new SQLException("No such schema " + schema.getName());

		// see if the table exists
		Table table = schema.getTable(sql.getTable().getName());
		if(table == null)
			throw new SQLException("Table " + schema.getName() + "." + sql.getTable().getName() + " does not exist");

		// create the tuple
		Tuple tuple = new Tuple(table.getAttributes().size());

		// convert the data into a tuple
		List<net.sf.jsqlparser.schema.Column> columns = sql.getColumns();
		for(net.sf.jsqlparser.schema.Column column : columns) {
			// make sure the column exists
			if(!table.attributeExists(column.getColumnName()))
				throw new SQLException("No such column " + table.getName() + "." + column.getColumnName());
			
			// put the data into the tuple
			tuple.set(table.getAttributeLocation(column.getColumnName()), 10);
		}

		// add tuple
		table.addTuple(tuple);

		return new Result(ResultCode.SUCCESS, null, null);
	}

}
