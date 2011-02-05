package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.create.database.CreateDatabase;
import net.eagledb.server.*;
import java.sql.*;

public class SQLCreateDatabase implements SQLAction {
	
	private CreateDatabase sql;

	private Server server;

	public SQLCreateDatabase(Server server, CreateDatabase sql) {
		this.sql = sql;
		this.server = server;
	}

	public Result getResult() throws SQLException {
		return new Result(ResultCode.SUCCESS, null, null);
	}

}
