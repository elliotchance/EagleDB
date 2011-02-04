package net.eagledb.test;

import java.sql.*;

public class ShowDatabases implements Test {

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("show databases");
		while(rs.next())
			System.out.println(rs.getString("database"));
		rs.close();
		st.close();
	}

}
