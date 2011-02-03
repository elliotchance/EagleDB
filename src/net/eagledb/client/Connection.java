package net.eagledb.client;

import net.eagledb.utils.Properties;
import java.io.*;
import java.net.*;
import net.eagledb.server.*;

public class Connection implements Serializable {

	private Properties parameters;

	private Socket socket;

	private DataOutputStream out;

	private BufferedReader in;

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
			socket = new Socket(parameters.get("host"), Integer.valueOf(parameters.get("port")));
			out = new DataOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String result = send("CONNECT " + parameters.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String send(String sql) throws Exception {
		out.writeBytes(sql + '\n');
		return in.readLine();
	}

	public String query(String sql) {
		try {
			return send(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
