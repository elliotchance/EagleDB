package net.eagledb;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import net.eagledb.server.EmbeddedServer;
import net.eagledb.server.sql.SQLParser;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;

public class Main {

	private String[] args;

	private static void usage() {
		System.err.println("\nUsage: java -jar eagledb.jar <mode> [<options>]\n");
		System.err.println("<mode> can be:");
		System.err.println("  client  Client instance");
		System.err.println("  server  Server instance");
		System.err.println("  embed   Start an embedded database and login with a client");
		System.err.println("    embed <user> [<pass> [<database> [<options]]]");
		System.err.println("  gui     Run the administrative tools\n");
		System.exit(1);
	}

	public static void main(String[] args) {
		if(args.length < 1)
			usage();

		new Main(args).run();
	}

	public Main(String[] args) {
		this.args = args;
	}

	public void run() {
		if(args[0].toLowerCase().equals("client"))
			client();
		else if(args[0].toLowerCase().equals("server"))
			server();
		else if(args[0].toLowerCase().equals("embed"))
			embed();
		else if(args[0].toLowerCase().equals("gui"))
			gui();
		else
			System.out.println("Unknown mode '" + args[0] + "'");
	}

	public final void client() {
		System.err.println("'client' mode is not supported yet.");
	}

	public final void server() {
		System.err.println("'server' mode is not supported yet.");
	}

	private void renderResultSet(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();

			for(int i = 0; i < cols; ++i) {
				if(i > 0)
					System.out.print("|");
				System.out.print(" " + rsmd.getColumnName(i) + " ");
			}
			System.out.println();

			for(int i = 0; i < cols; ++i) {
				if(i > 0)
					System.out.print("|");
				for(int j = 0; j < rsmd.getColumnName(i).length() + 2; ++j)
					System.out.print("-");
			}
			System.out.println();

			while(rs.next()) {
				for(int i = 0; i < cols; ++i) {
					if(i > 0)
						System.out.print(" | ");
					System.out.print(rs.getString(i));
				}
				System.out.println();
			}
			System.out.println();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public final void embed() {
		// start the embedded server
		EmbeddedServer server = new EmbeddedServer();

		// client connection
		String databaseUser = "";
		String databasePassword = "";
		String databaseName = "";
		Connection conn = null;
		try {
			Class.forName("net.eagledb.jdbc.Driver");

			if(args.length > 1)
				databaseUser = args[1];
			if(args.length > 2)
				databasePassword = args[2];
			if(args.length > 3)
				databaseName = args[3];

			conn = DriverManager.getConnection("eagledb://localhost/" + databaseName, databaseUser, databasePassword);
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch(ClassNotFoundException e) {
			System.err.println("Could not load net.eagledb.jdbc.Driver");
			System.exit(1);
		}

		// CLI
		Scanner in = new Scanner(System.in);
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		while(true) {
			System.out.print("eagledb " + databaseUser + "@" + databaseName + "> ");
			String sql = in.nextLine();

			// we need to know if we should use executeQuery() or executeUpdate(), easiest way to do that is by parsing
			// it
			net.sf.jsqlparser.statement.Statement sqlStmt = null;
			try {
				sqlStmt = parserManager.parse(new StringReader(sql));
			}
			catch(JSQLParserException e) {
				// do nothing
			}

			try {
				Statement stmt = conn.createStatement();

				// executeUpdate
				if(SQLParser.requiresUpdate(sqlStmt)) {
					int result = stmt.executeUpdate(sql);
					System.out.println("Query OK");
				}
				// executeQuery
				else {
					ResultSet rs = stmt.executeQuery(sql);
					renderResultSet(rs);
				}
			}
			catch(SQLException e) {
				System.out.println("ERROR: " + e.getMessage());
				continue;
			}
		}
	}

	public final void gui() {
		System.err.println("'gui' mode is not supported yet.");
	}

}
