package net.eagledb.test;

import net.eagledb.client.*;

/**
 * @brief Simulate a client accessing the server.
 * @author Elliot Chance
 */
public class Client extends Thread {

	@Override
	public void run() {
		try {
			Class.forName("net.eagledb.jdbc.Driver").newInstance();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(InstantiationException e) {
			e.printStackTrace();
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}

		// connect
		/*Connection conn = new Connection("user=root,pass=123");

		// send query
		String result = conn.query("create database mydb");
		System.out.print(result);*/
	}

}
