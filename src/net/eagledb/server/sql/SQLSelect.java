package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.select.Select;
import net.eagledb.server.*;
import java.sql.*;
import net.eagledb.server.storage.*;
import java.util.*;
import net.eagledb.server.planner.*;
import net.sf.jsqlparser.statement.select.*;

public class SQLSelect extends SQLAction {
	
	private Select sql;

	public SQLSelect(Server server, ClientConnection conn, Select sql) {
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
		PlainSelect select = (PlainSelect) sql.getSelectBody();
		Table table = schema.getTable(select.getFromItem().toString());
		if(table == null)
			throw new SQLException("Table " + schema.getName() + "." + select.getFromItem().toString() + " does not exist");

		// create the tuple
		/*

		// convert the data into a tuple
		List<net.sf.jsqlparser.schema.Column> columns = sql.getColumns();
		for(net.sf.jsqlparser.schema.Column column : columns) {
			// make sure the column exists
			if(!table.attributeExists(column.getColumnName()))
				throw new SQLException("No such column " + table.getName() + "." + column.getColumnName());
			
			// put the data into the tuple
			tuple.set(table.getAttributeLocation(column.getColumnName()), 10);
		}*/

		Tuple[] tuples = new Tuple[1];
		tuples[0] = new Tuple(table.getAttributes().size());
		tuples[0].set(0, 16);
		tuples[0].set(1, 5467.45);

		return new Result(ResultCode.SUCCESS, table.getAttributes(), tuples);
	}

}
