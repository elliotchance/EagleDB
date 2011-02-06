package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.create.database.CreateDatabase;
import net.eagledb.server.*;
import java.sql.*;
import net.eagledb.server.storage.*;

public class SQLCreateDatabase extends SQLAction {
	
	private CreateDatabase sql;

	public SQLCreateDatabase(Server server, ClientConnection conn, CreateDatabase sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		Database db = server.getDatabase(sql.getName().toString());

		// does the database already exist
		if(db != null)
			throw new SQLException("Database " + db.getName() + " already exists.");

		// check the users permission
		if(!conn.getUser().canCreateDatabase)
			throw new SQLException("Permission denied. You must have the CREATE DATABASE privilege.");

		// create the database
		server.createDatabase(sql.getName().toString());

		return new Result(ResultCode.SUCCESS);
	}

}
