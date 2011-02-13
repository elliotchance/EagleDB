package net.eagledb.test.compare;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLDB implements Benchmark {

	private Connection conn = null;

	public void init() throws SQLException {
		// connect as a client
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:jar/hsqldb", "sa", "");
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
			e.printStackTrace();
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
		// delete database files
		new File("jar/hsqldb.log").delete();
		new File("jar/hsqldb.properties").delete();
		new File("jar/hsqldb.lck").delete();
		new File("jar/hsqldb.script").delete();
	}

}
