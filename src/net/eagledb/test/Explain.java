package net.eagledb.test;

import java.sql.*;

public class Explain implements Test {

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("explain " + Select.selectSQL);
		System.out.println("EXPLAIN " + Select.selectSQL);
		while(rs.next())
			System.out.println("  " + rs.getString(0));
		System.out.println();
		rs.close();
		
		rs = st.executeQuery("explain analyze " + Select.selectSQL);
		System.out.println("EXPLAIN ANALYZE " + Select.selectSQL);
		while(rs.next())
			System.out.println("  " + rs.getString(0));
		System.out.println();
		rs.close();
		
		st.close();
	}

}
