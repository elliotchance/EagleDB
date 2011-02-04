package net.eagledb.test;

import java.sql.*;

public class CreateDatabase implements Test {

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("create database mydb");
		rs.close();
		st.close();
	}

}
