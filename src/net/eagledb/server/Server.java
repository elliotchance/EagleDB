package net.eagledb.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import net.eagledb.server.storage.*;

public class Server {

	public static int PORT = 6612;

	public static final int MAJOR_VERSION = 1;

	public static final int MINOR_VERSION = 0;

	public ArrayList<Database> databases;

	public ArrayList<User> users;

	public String databaseLocation = ".";

	public Server() {
		init();
		initUsers();
		initDatabases();
	}
	
	public void start() {
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
		try {
			while(true) {
				try {
					Socket socket = server.accept();
					new ClientConnection(this, socket).run();
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
	
	private void init() {
		// make sure data/ exists
		new File(databaseLocation + "/data").mkdir();
	}

	private void initDatabases() {
		// create the eagledb database if its doesnt exist
		if(!new File(databaseLocation + "/data/eagledb").exists()) {
			new File(databaseLocation + "/data/eagledb").mkdir();
			new File(databaseLocation + "/data/eagledb/public").mkdir();
		}

		databases = new ArrayList<Database>();
		String[] dbs = new File(databaseLocation + "/data").list();
		for(String dbname : dbs) {
			// only want directories
			File fileDB = new File(databaseLocation + "/data/" + dbname);
			if(!fileDB.isDirectory())
				continue;

			Database db = new Database(dbname);
			
			String[] schemas = fileDB.list();
			for(String schema : schemas) {
				if(!new File(databaseLocation + "/data/" + dbname + "/" + schema).isDirectory())
					continue;
				
				db.schemas.add(new Schema(schema));
			}
				
			databases.add(db);
		}
	}

	private void initUsers() {
		users = new ArrayList<User>();
		users.add(new User("root", "123"));
	}

}
