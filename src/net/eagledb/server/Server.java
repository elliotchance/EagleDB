package net.eagledb.server;

public class Server {

	public Server() {
		System.out.println("Server started");
		try {
			while(true) {
				Thread.sleep(1000);
			}
		}
		catch(InterruptedException e) {
			System.out.println("Server stopped");
		}
	}

}
