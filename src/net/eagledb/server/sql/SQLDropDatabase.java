package net.eagledb.server.sql;

import java.sql.SQLException;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.Result;
import net.eagledb.server.ResultCode;
import net.eagledb.server.Server;
import net.sf.jsqlparser.statement.drop.Drop;

public class SQLDropDatabase extends SQLAction {
	
	private Drop sql;

	public SQLDropDatabase(Server server, ClientConnection conn, Drop sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// drop the database
		server.dropDatabase(sql.getName());
		return new Result(ResultCode.SUCCESS);
	}

}
