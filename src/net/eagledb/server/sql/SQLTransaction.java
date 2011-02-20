package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.transaction.*;
import net.eagledb.server.*;
import java.sql.*;
import net.eagledb.server.storage.*;

public class SQLTransaction extends SQLAction {
	
	private Transaction sql;

	public SQLTransaction(Server server, ClientConnection conn, Transaction sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// we must have a selected database
		Database selectedDatabase = conn.getSelectedDatabase();
		if(selectedDatabase == null)
			throw new SQLException("No database selected.");

		if(sql.getCommand() == TransactionCommand.BEGIN) {
			conn.transactionID = selectedDatabase.beginTransaction();
		}
		else if(sql.getCommand() == TransactionCommand.COMMIT) {
			selectedDatabase.commitTransaction(conn.transactionID);
		}
		else {
			throw new SQLException(sql + " not supported");
		}

		return new Result(ResultCode.SUCCESS);
	}

}
