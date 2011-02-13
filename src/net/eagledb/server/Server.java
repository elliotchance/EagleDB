package net.eagledb.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import net.eagledb.server.storage.Attribute;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Schema;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.page.TransactionPage;

public class Server {

	/**
	 * Server port number.
	 */
	public static int PORT = 6612;

	public static final int MAJOR_VERSION = 1;

	public static final int MINOR_VERSION = 0;

	private ArrayList<Database> databases;

	public ArrayList<User> users;

	public String databaseLocation = ".";

	private BackgroundWriter bgWriter;

	public Server() {
		init();
		initUsers();
		initDatabases();
		initBackgroundWriter();
	}

	private void initBackgroundWriter() {
		bgWriter = new BackgroundWriter(this);
		bgWriter.start();
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
		while(true) {
			try {
				Socket socket = server.accept();
				new ClientConnection(this, socket, null).start();
			}
			catch(IOException e) {
				e.printStackTrace();
				continue;
			}
		}

		// clean up
		/*try {
			server.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}*/
	}
	
	private void init() {
		// make sure data/ exists
		new File(databaseLocation + "/data").mkdir();

		// types
		net.eagledb.server.sql.type.SQLType.registerAll();
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
			for(String schemaName : schemas) {
				if(!new File(databaseLocation + "/data/" + dbname + "/" + schemaName).isDirectory())
					continue;
				
				Schema schema = new Schema(schemaName);

				// load tables
				String[] tables = new File(databaseLocation + "/data/" + dbname + "/" + schemaName).list();
				for(String tableName : tables) {
					if(!tableName.endsWith(".table"))
						continue;

					try {
						String tablePath = databaseLocation + "/data/" + dbname + "/" + schemaName + "/" + tableName;
						ObjectInputStream in = new ObjectInputStream(new FileInputStream(tablePath));
						Table table = (Table) in.readObject();
						table.initTransient();
						table.transactionPageHandle = new RandomAccessFile(databaseLocation +
							"/data/" + dbname + "/" + schemaName + "/" + table.getName() + ".t", "rw");

						// handles for attributes
						int i = 0;
						for(Attribute attribute : table.getAttributes()) {
							attribute.setDataHandle(new RandomAccessFile(databaseLocation +
								"/data/" + dbname + "/" + schemaName + "/" + table.getName() + "." + i, "rw"));
							++i;
						}

						// calculate how many pages this table is
						table.setTotalPages((int) (table.transactionPageHandle.length() /
							(long) (TransactionPage.TUPLES_PER_PAGE * 4)));

						schema.addTable(table);
						in.close();
					}
					catch(IOException e) {
						e.printStackTrace();
					}
					catch(ClassNotFoundException e) {
						e.printStackTrace();
					}
				}

				db.addSchema(schema);
			}
				
			databases.add(db);
		}
	}

	private synchronized void saveUsers() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(databaseLocation + "/data/users.xml"));
			out.writeObject(users);
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void loadUsers() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(databaseLocation + "/data/users.xml"));
			users = (ArrayList<User>) in.readObject();
			in.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initUsers() {
		users = new ArrayList<User>();

		// does the users.xml exist?
		if(!new File(databaseLocation + "/data/users.xml").exists()) {
			// we need to create the default root user and create the users.xml file
			User root = new User("root", "123");
			root.canShowDatabases = true;
			root.canCreateDatabase = true;
			root.canCreateTable = true;
			users.add(root);

			saveUsers();
		}

		loadUsers();
	}

	public Database getDatabase(String name) {
		for(Database db : databases) {
			if(db.getName().equals(name))
				return db;
		}
		return null;
	}

	public String[] getDatabaseNames() {
		String[] names = new String[databases.size()];
		int i = 0;
		for(Database db : databases)
			names[i++] = db.getName();
		return names;
	}

	public synchronized void createDatabase(String name) {
		new File(databaseLocation + "/data/" + name).mkdir();
		new File(databaseLocation + "/data/" + name + "/public").mkdir();
	}

	public synchronized void saveTable(String databaseName, String schemaName, Table table) {
		try {
			// create the transaction file
			table.transactionPageHandle = new RandomAccessFile(databaseLocation + "/data/" +
				databaseName + "/" + schemaName + "/" + table.getName() + ".t", "rw");
			table.transactionPageHandle.write(new byte[] {});

			// create the attribute files
			int i = 0;
			for(Attribute attribute : table.getAttributes()) {
				attribute.setDataHandle(new RandomAccessFile(databaseLocation + "/data/" +
					databaseName + "/" + schemaName + "/" + table.getName() + "." + i, "rw"));
				attribute.getDataHandle().write(new byte[] {});
				++i;
			}

			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(databaseLocation + "/data/" +
				databaseName + "/" + schemaName + "/" + table.getName() + ".table"));
			out.writeObject(table);
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Database> getDatabases() {
		return databases;
	}

}
