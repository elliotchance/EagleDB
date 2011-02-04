package net.eagledb.server;

/**
 * @brief Spawn a server instance in an embedded environment.
 * @author Elliot Chance
 */
public class EmbeddedServer {

	private Thread serverThread;

	public EmbeddedServer() {
		serverThread = new Thread() {
			@Override
			public void run() {
				new Server().start();
			}
		};
		serverThread.start();
	}

	public void stop() {
		if(serverThread != null && serverThread.isAlive())
			serverThread.interrupt();
	}

}
