package net.eagledb.server;

import java.io.*;
import java.net.*;

public class Server {

	public static int PORT = 6612;

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
						if(sql.equals("DISCONNECT"))
							break;

						String result = sql.toUpperCase() + '\n';

						// send result
						out.writeBytes(result);
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
