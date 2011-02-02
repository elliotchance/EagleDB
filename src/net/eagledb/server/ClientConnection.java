package net.eagledb.server;

import java.io.*;
import java.net.*;
import net.eagledb.utils.Properties;

public class ClientConnection extends Thread {

	private Server server;

	private Socket socket;

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
				String result = "";
				if(sql.startsWith("CONNECT ")) {
					// see if the users credentials are correct
					Properties p = new Properties(sql.substring(sql.indexOf(" ") + 1));
					boolean allowed = server.authenticator.verifyUser(p);

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
		}
	}

}
