package net.eagledb.server.sql;

import java.sql.*;
import java.util.*;
import net.eagledb.server.*;
import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.Page;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class SQLCreateTable extends SQLAction {
	
	private CreateTable sql;

	public SQLCreateTable(Server server, ClientConnection conn, CreateTable sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// we must have a selected database
		Database selectedDatabase = conn.getSelectedDatabase();
		if(selectedDatabase == null) {
			throw new SQLException("No database selected.");
		}

		// check the users permission
		if(!conn.getUser().canCreateTable) {
			throw new SQLException("Permission denied. You must have the CREATE TABLE privilege.");
		}

		// get schema
		String schemaName = "public";
		Schema schema = selectedDatabase.getSchema(schemaName);
		if(schema == null) {
			throw new SQLException("No such schema " + schemaName);
		}

		// if this is a temporary table we need to translate the name to something random
		TemporaryTable tt = null;
		if(sql.getTemporary()) {
			tt = new TemporaryTable(sql.getTable().getName());
		}
		else {
			tt = new TemporaryTable(sql.getTable().getName(), sql.getTable().getName());
		}

		// see if the table already exists
		Table table = schema.getTable(tt.internalName);
		if(table != null) {
			throw new SQLException("Table " + schema.getName() + "." + tt.internalName + " already exists");
		}

		// create the table object
		table = new Table(tt.internalName, new Attribute[] {});

		// add fields
		List<net.sf.jsqlparser.statement.create.table.ColumnDefinition> columns = sql.getColumnDefinitions();
		for(net.sf.jsqlparser.statement.create.table.ColumnDefinition column : columns) {
			// translate the SQL name into the correct internal type
			String sqlType = column.getColDataType().getDataType().toUpperCase();
			Class internalType = Page.getClassForType(sqlType);
			if(internalType == null) {
				throw new SQLException("Unknown SQL type '" + sqlType + "'");
			}

			Attribute attr = new Attribute(column.getColumnName(), internalType);
			table.addAttribute(attr);
		}

		// register its temporary status
		if(!tt.name.equals(tt.internalName)) {
			conn.addTemporaryTable(tt);
		}

		// create the table
		schema.addTable(table);

		// write to disk
		server.saveTable(selectedDatabase.getName(), schema.getName(), table);

		return new Result(ResultCode.SUCCESS);
	}

}
