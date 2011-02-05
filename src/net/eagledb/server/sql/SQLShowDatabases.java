package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.show.databases.ShowDatabases;
import net.eagledb.server.*;
import net.eagledb.server.storage.*;
import java.sql.*;

public class SQLShowDatabases implements SQLAction {
	
	private ShowDatabases sql;

	private Server server;

	public SQLShowDatabases(Server server, ShowDatabases sql) {
		this.sql = sql;
		this.server = server;
	}

	public Result getResult() throws SQLException {
		// setup column definitions
		Field[] fields = new Field[1];
		fields[0] = new Field("database", net.eagledb.server.sql.type.VarChar.class);

		// add tuples
		Tuple[] tuples = new Tuple[server.databases.size()];
		int i = 0;
		for(Database db : server.databases) {
			tuples[i] = new Tuple(fields.length);
			tuples[i].attributes[0] = db.name;
			++i;
		}

		return new Result(ResultCode.SUCCESS, fields, tuples);
	}

}
