package net.eagledb.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import net.eagledb.server.sql.SQLParser;
import net.eagledb.server.storage.Database;

public class ClientConnection extends Thread {

	private Server server;

	private Socket socket;

	private SQLParser parser;

	private User user;

	private Database selectedDatabase;

	public ClientConnection(Server s, Socket sock, Database selectedDatabase) {
		server = s;
		socket = sock;
		this.selectedDatabase = selectedDatabase;
		parser = new SQLParser(this, server);
	}

	@Override
	public void run() {
		try {
			// communication
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			while(true) {
				// get input
				Request request = (Request) in.readObject();
				if(request.requestAction == RequestAction.CLOSE_CONNECTION)
					break;

				// process
				Result result = new Result(ResultCode.UNKNOWN);
				try {
					result = parser.parse(request.sql);
				}
				catch(SQLException e) {
					result.sqlException = e.getMessage();
				}

				// send result
				out.writeObject(result);
				out.flush();
			}

			socket.close();
			//in.close();
			out.close();
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

	public Database getSelectedDatabase() {
		return selectedDatabase;
	}

	public void setSelectedDatabase(String dbname) {
		selectedDatabase = server.getDatabase(dbname);
	}

}
