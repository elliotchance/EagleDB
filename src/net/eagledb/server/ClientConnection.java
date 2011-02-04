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
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			while(true) {
				// get input
				Request request = (Request) in.readObject();
				if(request.requestAction == RequestAction.CLOSE_CONNECTION)
					break;

				// process
				Result result = parser.parse(request.sql);

				// send result
				out.writeObject(result);
			}

			socket.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
