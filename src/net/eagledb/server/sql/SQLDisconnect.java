package net.eagledb.server.sql;

import java.sql.SQLException;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.DisconnectClient;
import net.eagledb.server.Result;
import net.eagledb.server.Server;
import net.sf.jsqlparser.statement.disconnect.Disconnect;

public class SQLDisconnect extends SQLAction {
	
	private Disconnect sql;

	public SQLDisconnect(Server server, ClientConnection conn, Disconnect sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		conn.close();
		throw new DisconnectClient();
	}

}
