package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.show.databases.ShowDatabases;
import net.eagledb.server.*;
import net.eagledb.server.storage.*;
import java.sql.*;

public class SQLShowDatabases extends SQLAction {
	
	private ShowDatabases sql;

	public SQLShowDatabases(Server server, ClientConnection conn, ShowDatabases sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// this action requires SHOW DATABASES permission
		if(!conn.getUser().canShowDatabases)
			throw new SQLException("Permission denied. You must have SHOW DATABASES privilege.");

		// setup column definitions
		Attribute[] fields = new Attribute[1];
		fields[0] = new Attribute("database", net.eagledb.server.sql.type.VarChar.class);

		// add tuples
		Tuple[] tuples = new Tuple[server.getDatabaseNames().length];
		int i = 0;
		for(String db : server.getDatabaseNames()) {
			tuples[i] = new Tuple(fields.length);
			tuples[i].attributes[0] = db;
			++i;
		}

		return new Result(ResultCode.SUCCESS, fields, tuples);
	}

}
