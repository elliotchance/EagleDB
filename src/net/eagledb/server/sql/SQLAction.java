package net.eagledb.server.sql;

import java.sql.*;
import net.eagledb.server.*;

public abstract class SQLAction {

	protected Server server;

	protected ClientConnection conn;

	public abstract Result getResult() throws SQLException;

	public SQLAction(Server server, ClientConnection conn) {
		this.server = server;
		this.conn = conn;
	}

}
