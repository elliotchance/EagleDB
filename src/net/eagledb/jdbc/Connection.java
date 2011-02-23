package net.eagledb.jdbc;

import java.io.*;
import java.net.*;
import net.eagledb.server.*;
import java.sql.*;
import java.util.*;

public class Connection<T> implements java.sql.Connection {

	/**
	 * Connection parameters.
	 */
	private Properties parameters;

	/**
	 * Socket handle.
	 */
	private Socket socket;

	/**
	 * Socket output stream.
	 */
	private ObjectOutputStream out;

	/**
	 * Socket input stream.
	 */
	private ObjectInputStream in;

	/**
	 * COMMIT after each statement. Default is on.
	 */
	private boolean isAutoCommit = true;

	/**
	 * Make a connection.
	 * @param url URL.
	 * @param parameters Connection parameters.
	 * @throws SQLException If the socket or its respective streams could not connect.
	 */
	public Connection(String url, java.util.Properties parameters) throws SQLException {
		this.parameters = parameters;

		// default options
		if(parameters.get("host") == null)
			parameters.put("host", "localhost");
		if(parameters.get("port") == null)
			parameters.put("port", String.valueOf(Server.PORT));

		// try to get the name of the database from the URL
		String dbname = "";
		try {
			dbname = new URI(url).getPath();
			if(dbname == null)
				dbname = "";
			if(dbname.length() > 1)
				dbname = dbname.substring(1);
		}
		catch(URISyntaxException e) {
			// do nothing, we didn't get the database name
		}

		if(!dbname.equals(""))
			parameters.put("database", dbname);

		// setup socket and object streams
		for(int i = 1; i <= 3; ++i) {
			try {
				socket = new Socket(parameters.getProperty("host"), Integer.valueOf(parameters.getProperty("port")));
			}
			catch(UnknownHostException e) {
				throw new SQLException("Could not resolve host " + parameters.getProperty("host") + ":" +
					parameters.getProperty("port"));
			}
			catch(IOException e) {
				if(i == 3)
					throw new SQLException("Socket failed after " + i + " tries: " + e.getMessage());

				// wait for 1 second
				try {
					Thread.sleep(1000);
				}
				catch(InterruptedException e2) {
					break;
				}
			}
		}
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		}
		catch(IOException e) {
			throw new SQLException("Unable to initialise ObjectStreams on socket: " + e.getMessage());
		}

		// translate the properties into a SQL statement
		String paras = "'";
		Set<String> pnames = parameters.stringPropertyNames();
		int i = 0;
		for(String pname : pnames) {
			if(i > 0)
				paras += "','";
			paras += pname + "'='" + parameters.getProperty(pname);
			++i;
		}
		paras += "'";

		// attempt to connect
		try {
			sendSingularQuery(new Request("CONNECT " + paras));
		}
		catch(SQLException e) {
			String msg = "Permission denied for user " + parameters.get("user");
			if(parameters.getProperty("database") != null && !parameters.getProperty("database").equals(""))
				msg += "@" + parameters.getProperty("database");
			msg += ", using password ";
			if(parameters.get("password") != null && !parameters.get("password").equals(""))
				msg += "yes";
			else
				msg += "no";
			throw new SQLException(msg);
		}
	}

	/**
	 * Check if a database name has been supplied by the connection. This is important for the actions that should only
	 * occur when a database is active like COMMIT.
	 */
	private boolean hasSelectedDatabase() {
		return parameters != null && parameters.getProperty("database") != null &&
			!parameters.getProperty("database").equals("");
	}

	public final Result sendSingularQuery(Request request) throws SQLException {
		if(request == null)
			throw new SQLException("Cannot send null request.");

		try {
			out.writeObject(request);
			out.flush();
			Result result = (Result) in.readObject();

			if(result == null)
				throw new SQLException("Invalid response from server.");

			if(result.sqlException != null && !result.sqlException.equals(""))
				throw new SQLException(result.sqlException);

			if(result.code != ResultCode.SUCCESS)
				throw new SQLException("Failed with code: " + result.code);

			return result;
		}
		catch(IOException e) {
			throw new SQLException(e.getMessage());
		}
		catch(ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}
	
	public final Result sendQuery(Request request) throws SQLException {
		// auto commit?
		if(isAutoCommit && hasSelectedDatabase())
			sendSingularQuery(new Request("BEGIN TRANSACTION", RequestAction.UPDATE));

		Result result = sendSingularQuery(request);

		// auto commit?
		if(isAutoCommit && hasSelectedDatabase())
			commit();

		return result;
	}

	public Statement createStatement() throws SQLException {
		return new Statement(this);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String nativeSQL(String sql) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		isAutoCommit = autoCommit;
	}

	public boolean getAutoCommit() throws SQLException {
		return isAutoCommit;
	}

	public void commit() throws SQLException {
		sendSingularQuery(new Request("COMMIT TRANSACTION", RequestAction.UPDATE));
	}

	public void rollback() throws SQLException {
		sendSingularQuery(new Request("ROLLBACK TRANSACTION", RequestAction.UPDATE));
	}

	public void close() throws SQLException {
		sendSingularQuery(new Request("DISCONNECT"));
	}

	public boolean isClosed() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isReadOnly() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setCatalog(String catalog) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getCatalog() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setTransactionIsolation(int level) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getTransactionIsolation() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void clearWarnings() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
		throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Map<String,Class<?>> getTypeMap() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setTypeMap(Map<String,Class<?>> map) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setHoldability(int holdability) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getHoldability() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Savepoint setSavepoint() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
		throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
		int resultSetHoldability) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
		int resultSetHoldability) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}
								   
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Clob createClob() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Blob createBlob() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public NClob createNClob() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public SQLXML createSQLXML() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isValid(int timeout) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
	}

	public void setClientInfo(java.util.Properties properties) throws SQLClientInfoException {
	}

	public String getClientInfo(String name) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public java.util.Properties getClientInfo() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

}
