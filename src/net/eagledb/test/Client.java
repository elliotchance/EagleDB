package net.eagledb.test;

import net.eagledb.client.*;

/**
 * @brief Simulate a client accessing the server.
 * @author Elliot Chance
 */
public class Client extends Thread {

	@Override
	public void run() {
		// connect
		Connection conn = new Connection("user=root,pass=123");
	}

}
