package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.connect.Connect;
import net.eagledb.server.*;
import java.sql.*;
import java.util.*;
import net.eagledb.server.crypt.*;

public class SQLConnect implements SQLAction {
	
	private Connect sql;

	private Server server;

	public SQLConnect(Server server, Connect sql) {
		this.sql = sql;
		this.server = server;
	}

	public Result getResult() throws SQLException {
		Properties p = sql.getProperties();
		SHA1 crypt = new SHA1();

		for(User user : server.users) {
			String username = (String) p.get("user");
			String password = (String) p.get("password");

			if(user.getUsername().equals(username) &&
			   user.getHashedPassword().equals(crypt.crypt(password)))
				return new Result(ResultCode.SUCCESS, null, null);
		}

		// failed to authenticate user
		return new Result(ResultCode.FAILED, null, null);
	}

}
