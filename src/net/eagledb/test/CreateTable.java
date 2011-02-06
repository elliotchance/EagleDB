package net.eagledb.test;

import java.sql.*;

public class CreateTable implements Test {

	public void run(Connection conn) throws SQLException {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("create table mytable (id int, number real)");
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			// ignore, the table already exists
		}
	}

}
