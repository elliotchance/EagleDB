package net.eagledb.server;

import java.io.*;
import java.net.*;
import net.eagledb.server.sql.SQLParser;
import java.util.Properties;

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
				String result = "";
				if(sql.startsWith("CONNECT ")) {
					// see if the users credentials are correct
					/*Properties p = new Properties(sql.substring(sql.indexOf(" ") + 1));
					boolean allowed = server.authenticator.verifyUser(p);

					if(allowed)
						result = "YES";
					else
						result = "NO";*/
				}
				else if(sql.equals("DISCONNECT"))
					break;
				else {
					// parse SQL
					result = parser.parse(sql);
				}

				/*assertEquals(2, createTable.getColumnDefinitions().size());
				assertEquals("mycol", ((ColumnDefinition) createTable.getColumnDefinitions().get(0)).getColumnName());
				assertEquals("mycol2", ((ColumnDefinition) createTable.getColumnDefinitions().get(1)).getColumnName());
				assertEquals("PRIMARY KEY", ((Index) createTable.getIndexes().get(0)).getType());
				assertEquals("mycol", ((Index) createTable.getIndexes().get(0)).getColumnsNames().get(1));
				assertEquals(statement, ""+createTable);*/

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
