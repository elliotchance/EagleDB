package net.eagledb.server;

import java.io.*;
import java.net.*;
import net.eagledb.server.sql.SQLParser;

public class ClientConnection extends Thread {

	private Server server;

	private Socket socket;

	private SQLParser parser = new SQLParser();

	public ClientConnection(Server s, Socket sock) {
		server = s;
		socket = sock;
	}

	@Override
	public void run() {
		try {
			// communication
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			while(true) {
				// get input
				String sql = in.readLine();
				System.out.println("Received: " + sql);

				// process
				String result = parser.parse(sql);
				System.out.println("result = " + result);

				// send result
				out.writeBytes(result + '\n');
				break;
			}

			socket.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
