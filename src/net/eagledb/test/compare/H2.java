package net.eagledb.test.compare;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.Server;

public class H2 implements Benchmark {

	private Connection conn = null;

	private Server server = null;

	public void init() throws SQLException {
		// start the server
		server = Server.createTcpServer(new String[] {}).start();

		// connect as a client
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection("jdbc:h2:jar/h2db", "sa", "sa");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("init() failed");
		}
	}

	public void createTable() throws SQLException {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(createTableSQL);
			statement.close();
		}
		catch(SQLException e) {
			// the table already exists, no problem here
		}
	}

	public void insert() throws SQLException {
		Statement statement = conn.createStatement();
		for(int i = 0; i < testTuples; ++i)
			statement.executeUpdate("insert into mytable (id, number) values (" + i + ", " + Math.sqrt(i) + ")");
	}

	public void select(String selectSQL) throws SQLException {
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(selectSQL);
		//while(rs.next())
		//	System.out.println(rs.getInt(1) + " | " + rs.getFloat(2));
		rs.close();
		statement.close();
	}

	public void end() throws SQLException {
		server.stop();

		// delete database files
		new File("jar/h2db.h2.db").delete();
		new File("jar/h2db.lock.db").delete();
		new File("jar/h2db.trace.db").delete();
	}

}
