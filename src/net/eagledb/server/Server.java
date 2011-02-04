package net.eagledb.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import net.eagledb.server.storage.*;

public class Server {

	public static int PORT = 6612;

	public Authenticator authenticator;

	public static final int MAJOR_VERSION = 1;

	public static final int MINOR_VERSION = 0;

	public ArrayList<Database> databases;

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

		// setup databases
		databases = new ArrayList<Database>();
		databases.add(new Database("eagledb"));
		databases.add(new Database("test"));

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
