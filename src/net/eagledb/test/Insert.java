package net.eagledb.test;

import java.sql.*;

public class Insert implements Test {

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();

		// insert a bunch of records
		for(int i = 10; i < 1000; i += 10) {
			ResultSet rs = st.executeQuery("insert into mytable (id, number) values (" + i + ", " + Math.sqrt(i) + ")");
			rs.close();
		}

		st.close();
	}

}
