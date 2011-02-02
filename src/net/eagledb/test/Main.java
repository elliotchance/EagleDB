package net.eagledb.test;

import net.eagledb.server.*;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		// start the server
        EmbeddedServer server = new EmbeddedServer();

		// start the clients
		Client[] clients = new Client[1];
		for(int i = 0; i < clients.length; ++i) {
			clients[i] = new Client();
			clients[i].start();
		}

		// wait for all the work to finish
		try {
			for(int i = 0; i < clients.length; ++i)
				clients[i].join();
		}
		catch(InterruptedException e) {
			// do nothing
		}
		server.stop();
    }

}
