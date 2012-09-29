package net.eagledb.server.sql;

import java.sql.*;
import net.eagledb.server.*;
import net.eagledb.server.storage.*;
import net.sf.jsqlparser.statement.create.database.CreateDatabase;

public class SQLCreateDatabase extends SQLAction {
	
	private CreateDatabase sql;

	public SQLCreateDatabase(Server server, ClientConnection conn, CreateDatabase sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// does the database already exist
		if(server.getDatabase(sql.getName()) != null) {
			throw new SQLException("Database " + sql.getName() + " already exists.");
		}

		// check the users permission
		if(!conn.getUser().canCreateDatabase) {
			throw new SQLException("Permission denied. You must have the CREATE DATABASE privilege.");
		}

		// create the database
		Database newDB = server.createDatabase(sql.getName());

		// create the public schema
		newDB.addSchema(new Schema("public"));
		server.createSchema(sql.getName(), "public");

		return new Result(ResultCode.SUCCESS);
	}

}
