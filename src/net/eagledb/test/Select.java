package net.eagledb.test;

import java.sql.*;

public class Select implements Test {

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select id, number from mytable where id>10 and id<20");
		while(rs.next()) {
			System.out.println(rs.getInt(0) + " | " + rs.getFloat(1));
		}
		rs.close();
		st.close();
	}

}
