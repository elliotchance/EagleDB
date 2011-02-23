package net.eagledb.server.sql;

import java.sql.SQLException;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.Result;
import net.eagledb.server.ResultCode;
import net.eagledb.server.Server;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Index;
import net.sf.jsqlparser.statement.create.index.CreateIndex;

public class SQLCreateIndex extends SQLAction {
	
	private CreateIndex sql;

	public SQLCreateIndex(Server server, ClientConnection conn, CreateIndex sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// we must have a selected database
		Database selectedDatabase = conn.getSelectedDatabase();
		if(selectedDatabase == null)
			throw new SQLException("No database selected.");

		// check the users permission
		//if(!conn.getUser().canCreateDatabase)
		//	throw new SQLException("Permission denied. You must have the CREATE DATABASE privilege.");

		// create the index
		Index index = new Index();
		index.name = sql.getIndex();
		index.table = sql.getTable();
		index.columns = sql.getColumns();

		// add the index
		selectedDatabase.addIndex(index);

		return new Result(ResultCode.SUCCESS);
	}

}
