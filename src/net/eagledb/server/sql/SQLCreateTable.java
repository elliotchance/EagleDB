package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.eagledb.server.*;
import java.sql.*;
import net.eagledb.server.storage.*;

public class SQLCreateTable extends SQLAction {
	
	private CreateTable sql;

	public SQLCreateTable(Server server, ClientConnection conn, CreateTable sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// we must have a selected database
		if(conn.getSelectedDatabase() == null)
			throw new SQLException("No database selected.");

		throw new SQLException("Database verified");
		
		/*Database db = server.getDatabase(sql.getName().toString());

		// does the database already exist
		if(db != null)
			throw new SQLException("Database " + db.name + " already exists.");

		// check the users permission
		if(!conn.getUser().canCreateDatabase)
			throw new SQLException("Permission denied. You must have the CREATE DATABASE privilege.");

		// create the database
		server.createDatabase(sql.getName().toString());*/

		//return new Result(ResultCode.SUCCESS, null, null);
	}

}
