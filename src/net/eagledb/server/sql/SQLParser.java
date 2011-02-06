package net.eagledb.server.sql;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import java.io.StringReader;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.show.databases.ShowDatabases;
import net.sf.jsqlparser.statement.create.database.CreateDatabase;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.connect.Connect;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import java.sql.*;
import net.eagledb.server.*;

public class SQLParser {

	private CCJSqlParserManager parserManager = new CCJSqlParserManager();

	private Server server;

	private ClientConnection conn;

	public SQLParser(ClientConnection conn, Server server) {
		this.server = server;
		this.conn = conn;
	}

	public Result parse(String sql) throws SQLException {
		try {
			Result result = new Result(ResultCode.UNKNOWN);

			Statement stmt = parserManager.parse(new StringReader(sql));
			if(stmt instanceof Connect)
				result = new SQLConnect(server, conn, (Connect) stmt).getResult();
			else if(stmt instanceof CreateDatabase)
				result = new SQLCreateDatabase(server, conn, (CreateDatabase) stmt).getResult();
			else if(stmt instanceof CreateTable)
				result = new SQLCreateTable(server, conn, (CreateTable) stmt).getResult();
			else if(stmt instanceof Insert)
				result = new SQLInsert(server, conn, (Insert) stmt).getResult();
			else if(stmt instanceof Select)
				result = new SQLSelect(server, conn, (Select) stmt).getResult();
			else if(stmt instanceof ShowDatabases)
				result = new SQLShowDatabases(server, conn, (ShowDatabases) stmt).getResult();
			else
				throw new SQLException("Invalid SQL: " + sql);

			return result;
		}
		catch(JSQLParserException e) {
			throw new SQLException(e.getCause().toString());
		}
		catch(SQLException e) {
			throw e;
		}
	}

}
