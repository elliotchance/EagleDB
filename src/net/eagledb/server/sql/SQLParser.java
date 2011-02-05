package net.eagledb.server.sql;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import java.io.StringReader;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.show.databases.ShowDatabases;
import net.sf.jsqlparser.statement.create.database.CreateDatabase;
import net.sf.jsqlparser.statement.connect.Connect;
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
			if(stmt instanceof Connect)
				result = new SQLConnect(server, (Connect) stmt).getResult();
			else if(stmt instanceof CreateDatabase)
				result = new SQLCreateDatabase(server, (CreateDatabase) stmt).getResult();
			else if(stmt instanceof ShowDatabases)
				result = new SQLShowDatabases(server, (ShowDatabases) stmt).getResult();
			else
				result.error = "Unknown SQL: " + sql + "\n" + "Parser object: " + stmt;
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
