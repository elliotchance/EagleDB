package net.eagledb.server;

import java.io.*;
import java.net.*;

public class Server {

	public static int PORT = 6612;

	public Authenticator authenticator;

	public static final int MAJOR_VERSION = 1;

	public static final int MINOR_VERSION = 0;

	public Server() {
		// start the server
		ServerSocket server = null;
		try {
			server = new ServerSocket(Server.PORT);
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}

		// setup users
		authenticator = new Authenticator();

		// wait for connections
		try {
			while(true) {
				try {
					Socket socket = server.accept();
					new ClientConnection(this, socket).run();
				}
				catch(IOException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		catch(Exception e) {
			System.out.println("Server stopped");
		}

		// clean up
		try {
			server.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
	}

}
