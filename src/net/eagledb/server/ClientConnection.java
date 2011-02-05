package net.eagledb.server;

import java.io.*;
import java.net.*;
import net.eagledb.server.sql.SQLParser;
import java.sql.*;

public class ClientConnection extends Thread {

	private Server server;

	private Socket socket;

	private SQLParser parser;

	private User user;

	public ClientConnection(Server s, Socket sock) {
		server = s;
		socket = sock;
		parser = new SQLParser(this, server);
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
				Result result = new Result();
				try {
					result = parser.parse(request.sql);
				}
				catch(SQLException e) {
					result.error = e.getMessage();
				}

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

	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		user = u;
	}

}
