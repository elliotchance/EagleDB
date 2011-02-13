package net.eagledb.test.compare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.eagledb.server.EmbeddedServer;

public class EagleDB implements Benchmark {

	private Connection conn = null;

	private EmbeddedServer server = null;

	public void init() throws SQLException {
		// start the embedded server first
		server = new EmbeddedServer();

		// now connect as a client
		try {
			Class.forName("net.eagledb.jdbc.Driver");
			conn = DriverManager.getConnection("eagledb://localhost/eagledb", "root", "123");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("init() failed");
		}
	}

	public void createTable() throws SQLException {
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(createTableSQL);
			rs.close();
			statement.close();
		}
		catch(SQLException e) {
			// the table already exists, no problem here
		}
	}

	public void insert() throws SQLException {
		Statement statement = conn.createStatement();
		for(int i = 0; i < testTuples; ++i)
			statement.executeQuery("insert into mytable (id, number) values (" + i + ", " + Math.sqrt(i) + ")");
	}

	public void select(String selectSQL) throws SQLException {
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(selectSQL);
		//while(rs.next())
		//	System.out.println(rs.getInt(0) + " | " + rs.getFloat(1));
		rs.close();
		statement.close();
	}

	public void end() throws SQLException {
		server.stop();
	}

}
