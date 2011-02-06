package net.eagledb.server.sql;

import net.eagledb.server.*;
import java.sql.*;
import net.eagledb.server.storage.*;
import net.eagledb.server.storage.page.*;
import net.eagledb.server.planner.*;
import net.sf.jsqlparser.statement.select.*;
import net.eagledb.server.sql.type.*;

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

		// expression operations: WHERE id>700 AND id<800
		PageOperation[] op = new PageOperation[] {
			new PageScan(0, table.getAttributeLocation("id"), PageAction.GREATER_THAN, 1800),
			new PageScan(1, table.getAttributeLocation("id"), PageAction.LESS_THAN, 1810),
			new PageCompare(0, 1, 2, PageAction.AND)
		};

		// create the executation plan
		Plan p = new Plan();
		p.plan.add(new FullTableScan(table, op));
		p.plan.add(new FetchAttributes(table,
			new int[] { 0, 1 },
			new Class[] { net.eagledb.server.sql.type.Integer.class, net.eagledb.server.sql.type.Real.class }
		));
		System.out.println(p);
		p.execute();

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

		return new Result(ResultCode.SUCCESS, table.getAttributes(), p.getTuples());
	}

}
