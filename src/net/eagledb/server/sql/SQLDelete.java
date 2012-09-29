package net.eagledb.server.sql;

import java.sql.SQLException;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.Result;
import net.eagledb.server.ResultCode;
import net.eagledb.server.Server;
import net.eagledb.server.planner.ExpressionException;
import net.eagledb.server.planner.FullTableScan;
import net.eagledb.server.planner.PageOperation;
import net.eagledb.server.planner.Plan;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Schema;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.TemporaryTable;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.statement.delete.Delete;

public class SQLDelete extends SQLAction {
	
	private Delete sql;

	public SQLDelete(Server server, ClientConnection conn, Delete sql) {
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
		//if(!conn.getUser().canCreateTable)
		//	throw new SQLException("Permission denied. You must have the CREATE TABLE privilege.");

		// get schema
		Schema schema = selectedDatabase.getSchema("public");
		if(schema == null) {
			throw new SQLException("No such schema " + schema.getName());
		}

		// see if the table exists
		Table table = schema.getTable(sql.getTable().toString());
		if(table == null) {
			// temporary table?
			TemporaryTable tt = conn.getTemporaryTable(sql.getTable().toString());
			if(tt != null) {
				table = schema.getTable(tt.internalName);
			}

			if(table == null) {
				throw new SQLException("Table " + schema.getName() + "." + sql.getTable().toString() +
					" does not exist");
			}
		}

		// parse expression operations
		try {
			// extract WHERE clause, making sure it is not empty
			Expression whereClause = sql.getWhere();
			if(whereClause == null) {
				whereClause = new LongValue("1");
			}

			// parse the expression
			net.eagledb.server.planner.Expression ex = new net.eagledb.server.planner.Expression(table,
				selectedDatabase, whereClause);
			PageOperation[] op = ex.parse(true);

			// create the executation plan
			Plan p = new Plan();

			// find limits
			int limitOffset = 0, limit = Integer.MAX_VALUE;
			if(sql.getLimit() != null) {
				limitOffset = (int) sql.getLimit().getOffset();
				limit = (int) sql.getLimit().getRowCount();
			}

			p.addPlanItem(new FullTableScan(conn, table, 0, whereClause.toString(), op, ex.buffers, limitOffset,
				limit));

			// execute plan
			p.executeDelete(conn.transactionID);

			return new Result(ResultCode.SUCCESS);
		}
		catch(ExpressionException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return new Result(ResultCode.FAILED);
	}

}
