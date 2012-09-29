package net.eagledb.server.sql;

import java.sql.*;
import net.eagledb.server.*;
import net.sf.jsqlparser.statement.block.Block;

public class SQLBlock extends SQLAction {
	
	private Block sql;

	private SQLParser parser;

	public SQLBlock(Server server, ClientConnection conn, SQLParser parser, Block sql) {
		super(server, conn);
		this.sql = sql;
		this.parser = parser;
	}

	public Result getResult() throws SQLException {
		// check the users permission
		//if(!conn.getUser().canCreateDatabase)
		//	throw new SQLException("Permission denied.");

		// execute
		for(net.sf.jsqlparser.statement.Statement s : sql.getStatements()) {
			if(SQLParser.requiresUpdate(s)) {
				parser.parse(s.toString(), RequestAction.UPDATE);
			}
			else {
				parser.parse(s.toString(), RequestAction.QUERY);
			}
		}

		return new Result(ResultCode.SUCCESS);
	}

}
