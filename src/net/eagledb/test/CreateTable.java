package net.eagledb.test;

import java.sql.*;

public class CreateTable implements Test {

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("create table mytable (id int, number double)");
		rs.close();
		st.close();
	}

}
