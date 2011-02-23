package net.eagledb.server.sql;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import java.io.StringReader;
import java.sql.SQLException;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.DisconnectClient;
import net.eagledb.server.RequestAction;
import net.eagledb.server.Result;
import net.eagledb.server.ResultCode;
import net.eagledb.server.Server;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.show.Show;
import net.sf.jsqlparser.statement.create.database.CreateDatabase;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.connect.Connect;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.disconnect.Disconnect;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.transaction.Transaction;

public class SQLParser {

	private CCJSqlParserManager parserManager = new CCJSqlParserManager();

	private Server server;

	private ClientConnection conn;

	public SQLParser(ClientConnection conn, Server server) {
		this.server = server;
		this.conn = conn;
	}

	public static boolean requiresUpdate(Statement stmt) {
		if(stmt instanceof CreateDatabase)
			return true;
		if(stmt instanceof CreateIndex)
			return true;
		if(stmt instanceof CreateTable)
			return true;
		if(stmt instanceof Insert)
			return true;
		if(stmt instanceof Drop)
			return true;
		if(stmt instanceof Transaction)
			return true;
		return false;
	}

	public static String getRawName(String entity) {
		if(entity.startsWith("\""))
			return entity.substring(1, entity.length() - 1);
		return entity;
	}

	public Result parse(String sql, int action) throws SQLException {
		try {
			Result result = new Result(ResultCode.UNKNOWN);
			Statement stmt = parserManager.parse(new StringReader(sql));

			if(stmt instanceof Disconnect)
				return new SQLDisconnect(server, conn, (Disconnect) stmt).getResult();

			// executeQuery() vs executeUpdate()
			if(requiresUpdate(stmt) && action != RequestAction.UPDATE)
				throw new SQLException("You must use executeUpdate() for modification queries.");

			if(stmt instanceof Connect)
				result = new SQLConnect(server, conn, (Connect) stmt).getResult();
			else if(stmt instanceof CreateDatabase)
				result = new SQLCreateDatabase(server, conn, (CreateDatabase) stmt).getResult();
			else if(stmt instanceof CreateIndex)
				result = new SQLCreateIndex(server, conn, (CreateIndex) stmt).getResult();
			else if(stmt instanceof CreateTable)
				result = new SQLCreateTable(server, conn, (CreateTable) stmt).getResult();
			else if(stmt instanceof Drop) {
				Drop newStmt = (Drop) stmt;
				if(newStmt.getType().toUpperCase().equals("DATABASE"))
					result = new SQLDropDatabase(server, conn, newStmt).getResult();
				else if(newStmt.getType().toUpperCase().equals("TABLE"))
					result = new SQLDropTable(server, conn, newStmt).getResult();
				else
					throw new SQLException("Invalid SQL: " + sql);
			}
			else if(stmt instanceof Insert)
				result = new SQLInsert(server, conn, (Insert) stmt).getResult();
			else if(stmt instanceof Select)
				result = new SQLSelect(server, conn, (Select) stmt).getResult();
			else if(stmt instanceof Show)
				result = new SQLShowDatabases(server, conn, (Show) stmt).getResult();
			else if(stmt instanceof Transaction)
				result = new SQLTransaction(server, conn, (Transaction) stmt).getResult();
			else
				throw new SQLException("Invalid SQL: " + sql);

			return result;
		}
		catch(DisconnectClient e) {
			throw e;
		}
		catch(JSQLParserException e) {
			throw new SQLException(e.getCause().toString());
		}
		catch(SQLException e) {
			throw e;
		}
	}

}
