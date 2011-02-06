package net.eagledb.test;

import java.sql.*;

public class Insert implements Test {

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("insert into mytable (id, number) values (15, 67.23)");
		rs.close();
		st.close();
	}

}
