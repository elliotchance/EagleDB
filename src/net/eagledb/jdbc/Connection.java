package net.eagledb.jdbc;

import java.io.*;
import java.net.*;
import net.eagledb.server.*;
import java.sql.*;
import java.util.*;

public class Connection<T> implements java.sql.Connection {

	private Properties parameters;

	private Socket socket;

	private DataOutputStream out;

	private BufferedReader in;

	public Connection(String url, java.util.Properties parameters) {
		// default options
		if(parameters.get("host") == null)
			parameters.put("host", "localhost");
		if(parameters.get("port") == null)
			parameters.put("port", String.valueOf(Server.PORT));

		// attempt to connect
		try {
			// communication
			socket = new Socket(parameters.getProperty("host"), Integer.valueOf(parameters.getProperty("port")));
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

	public Statement createStatement() throws SQLException {
		return new Statement();
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