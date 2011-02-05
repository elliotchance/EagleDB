package net.eagledb.jdbc;

import java.io.*;
import java.net.*;
import net.eagledb.server.*;
import java.sql.*;
import java.util.*;

public class Connection<T> implements java.sql.Connection {

	private Properties parameters;

	private Socket socket;

	private ObjectOutputStream out;

	private ObjectInputStream in;

	public Connection(String url, java.util.Properties parameters) throws SQLException {
		// default options
		if(parameters.get("host") == null)
			parameters.put("host", "localhost");
		if(parameters.get("port") == null)
			parameters.put("port", String.valueOf(Server.PORT));

		// setup socket and object streams
		try {
			socket = new Socket(parameters.getProperty("host"), Integer.valueOf(parameters.getProperty("port")));
		}
		catch(UnknownHostException e) {
			throw new SQLException("Could not resolve host " + parameters.getProperty("host") + ":" +
				parameters.getProperty("port"));
		}
		catch(IOException e) {
			throw new SQLException("Socket failed: " + e.getMessage());
		}
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		}
		catch(IOException e) {
			throw new SQLException("Unable to initialise ObjectStreams on socket: " + e.getMessage());
		}

		// translate the properties into a SQL statement
		String paras = "";
		Set<String> pnames = parameters.stringPropertyNames();
		int i = 0;
		for(String pname : pnames) {
			if(i > 0)
				paras += ",";
			paras += pname + "=" + parameters.getProperty(pname);
			++i;
		}

		// attempt to connect
		try {
			Result result = sendQuery(new Request("CONNECT " + paras));
		}
		catch(SQLException e) {
			String msg = "Permission denied for user " + parameters.get("user") + ", using password ";
			if(parameters.get("password") != null && !parameters.get("password").equals(""))
				msg += "yes";
			else
				msg += "no";
			throw new SQLException(msg);
		}
	}
	
	public Result sendQuery(Request request) throws SQLException {
		try {
			out.writeObject(request);
			Result result = (Result) in.readObject();
			
			if(result.error != null)
				throw new SQLException(result.error + "\nSQL " + request.sql);

			if(result.code != ResultCode.SUCCESS)
				throw new SQLException("Failed with code: " + result.code + "\nSQL " + request.sql);
			
			return result;
		}
		catch(IOException e) {
			throw new SQLException(e.getMessage());
		}
		catch(ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
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
		throw new SQLFeatureNotSupportedException();
	}

	public boolean getAutoCommit() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void commit() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void rollback() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void close() throws SQLException {
		throw new SQLFeatureNotSupportedException();
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
