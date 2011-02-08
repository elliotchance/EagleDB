package net.eagledb.test;

import java.sql.*;
import java.util.Calendar;

public class Insert implements Test {

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();

		// insert a bunch of records
		long start = Calendar.getInstance().getTimeInMillis();
		int tuples = 50;
		for(int i = 0; i < tuples; ++i) {
			ResultSet rs = st.executeQuery("insert into mytable (id, number) values (" + i + ", " + Math.sqrt(i) + ")");
			rs.close();
		}
		long time = Calendar.getInstance().getTimeInMillis() - start;
		System.out.println("INSERT: " + (int) (tuples / (time / 1000.0)) + " per second");

		st.close();
	}

}
