package net.eagledb.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Select implements Test {

	public static String selectSQL = "select id, number, id from mytable where id>10 and id<20";

	public void run(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(selectSQL);
		while(rs.next())
			System.out.println(rs.getInt(0) + " | " + rs.getFloat(1));
		rs.close();
		st.close();
	}

}
