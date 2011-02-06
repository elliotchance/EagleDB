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
			Connection conn = DriverManager.getConnection("eagledb://localhost/eagledb", "root", "123");

			//new CreateDatabase().run(conn);
			//new ShowDatabases().run(conn);
			new CreateTable().run(conn);
			new Insert().run(conn);
			new Select().run(conn);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
