package net.eagledb.server.sql;

import java.sql.SQLException;
import java.util.List;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.Result;
import net.eagledb.server.ResultCode;
import net.eagledb.server.Server;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Schema;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.TemporaryTable;
import net.eagledb.server.storage.Tuple;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

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
		String schemaName = "public";
		Schema schema = selectedDatabase.getSchema(schemaName);
		if(schema == null)
			throw new SQLException("No such schema " + schemaName);

		// see if the table exists
		Table table = schema.getTable(sql.getTable().getName());
		if(table == null) {
			// temporary table?
			TemporaryTable tt = conn.getTemporaryTable(sql.getTable().getName());
			if(tt != null)
				table = schema.getTable(tt.internalName);

			if(table == null)
				throw new SQLException("Table " + schemaName + "." + sql.getTable().getName() + " does not exist");
		}

		// create the tuple
		Tuple tuple = new Tuple(table.getAttributes().length);

		// convert the data into a tuple
		List<net.sf.jsqlparser.schema.Column> columns = sql.getColumns();
		int i = 0;
		for(net.sf.jsqlparser.schema.Column column : columns) {
			// make sure the column exists
			if(!table.attributeExists(column.getColumnName()))
				throw new SQLException("No such column " + table.getName() + "." + column.getColumnName());
			
			// put the data into the tuple
			double value = Double.valueOf(((ExpressionList) sql.getItemsList()).getExpressions().get(i).toString());
			tuple.set(table.getAttributeLocation(column.getColumnName()), value);
			++i;
		}

		// add tuple
		table.addTuple(tuple, conn.transactionID);

		return new Result(ResultCode.SUCCESS);
	}

}
