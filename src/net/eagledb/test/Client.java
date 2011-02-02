package net.eagledb.test;

/**
 * @brief Simulate a client accessing the server.
 * @author Elliot Chance
 */
public class Client extends Thread {

	@Override
	public void run() {
		System.out.println("Starting client");
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			// do nothing
		}
		System.out.println("Client finished");
	}

}
