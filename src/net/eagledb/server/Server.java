package net.eagledb.server;

import java.io.*;
import java.net.*;
import net.eagledb.utils.Properties;

public class Server {

	public static int PORT = 6612;

	private Authenticator authenticator;

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
		String data = "Toobie ornaught toobie";
		try {
			while(true) {
				try {
					// communication
					Socket socket = server.accept();
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());

					while(true) {
						// get input
						String sql = in.readLine();
						System.out.println("Received: " + sql);

						// process
						String result = "";
						if(sql.startsWith("CONNECT ")) {
							// see if the users credentials are correct
							Properties p = new Properties(sql.substring(sql.indexOf(" ") + 1));
							boolean allowed = authenticator.verifyUser(p);

							if(allowed)
								result = "YES";
							else
								result = "NO";
						}

						if(sql.equals("DISCONNECT"))
							break;

						// send result
						out.writeBytes(result + '\n');
					}
					
					socket.close();
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
