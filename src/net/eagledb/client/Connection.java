package net.eagledb.client;

import net.eagledb.utils.Properties;
import java.io.*;
import java.net.*;
import net.eagledb.server.*;

public class Connection implements Serializable {

	private Properties parameters;

	public Connection(String connectionString) {
		// get parameters
		parameters = new Properties(connectionString);

		// default options
		if(parameters.get("host") == null)
			parameters.put("host", "localhost");
		if(parameters.get("port") == null)
			parameters.put("port", String.valueOf(Server.PORT));

		// attempt to connect
		try {
			// communication
			Socket socket = new Socket(parameters.get("host"), Integer.valueOf(parameters.get("port")));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// send SQL
			out.writeBytes("CONNECT " + parameters.toString() + '\n');

			// get result
			String result = in.readLine();
			System.out.println("FROM SERVER: " + result);

			// clean up
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
