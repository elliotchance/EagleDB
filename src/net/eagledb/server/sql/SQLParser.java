package net.eagledb.server.sql;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import java.io.StringReader;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.show.databases.ShowDatabases;
import java.sql.*;
import net.eagledb.server.*;

public class SQLParser {

	private CCJSqlParserManager parserManager = new CCJSqlParserManager();

	private Server server;

	public SQLParser(Server server) {
		this.server = server;
	}

	public Result parse(String sql) {
		Result result = new Result();

		try {
			Statement stmt = parserManager.parse(new StringReader(sql));
			if(stmt instanceof ShowDatabases)
				result = new SQLShowDatabases(server, (ShowDatabases) stmt).getResult();
			else
				result.error = "Unknown SQL: " + sql;
		}
		catch(JSQLParserException e) {
			result.error = e.getCause().toString();
		}
		catch(SQLException e) {
			result.error = e.getCause().toString();
		}

		return result;
	}

}
