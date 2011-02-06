package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.connect.Connect;
import net.eagledb.server.*;
import java.sql.*;
import java.util.*;
import net.eagledb.server.crypt.*;

public class SQLConnect extends SQLAction {
	
	private Connect sql;

	public SQLConnect(Server server, ClientConnection conn, Connect sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		Properties p = sql.getProperties();
		SHA1 crypt = new SHA1();

		for(User user : server.users) {
			String username = p.getProperty("user");
			String password = p.getProperty("password");

			if(user.getUsername().equals(username) &&
			   user.getHashedPassword().equals(crypt.crypt(password))) {
				// update the currently privilaged user
				conn.setUser(user);
				if(!p.getProperty("database").equals(""))
					conn.setSelectedDatabase(p.getProperty("database"));

				// return success
				return new Result(ResultCode.SUCCESS, null, null);
			}
		}

		// failed to authenticate user
		return new Result(ResultCode.FAILED, null, null);
	}

}
