package net.eagledb.test;

import java.sql.*;

/**
 * @brief Simulate a client accessing the server.
 * @author Elliot Chance
 */
public class Client extends Thread {

	@Override
	public void run() {
		try {
			Class.forName("net.eagledb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("eagledb://localhost", "root", "123");

			new CreateDatabase().run(conn);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		// connect
		/*Connection conn = new Connection("user=root,pass=123");

		// send query
		String result = conn.query("create database mydb");
		System.out.print(result);*/
	}

}
