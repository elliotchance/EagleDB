package net.eagledb.jdbc;

import java.sql.*;
import net.eagledb.server.storage.*;

public class Statement implements java.sql.Statement {

	public ResultSet executeQuery(String sql) throws SQLException {
		// setup column definitions
		Field[] fields = new Field[1];
		fields[0] = new Field("id", IntPage.class);
		
		// add tuples
		Tuple[] tuples = new Tuple[3];
		tuples[0] = new Tuple(fields.length);
		tuples[1] = new Tuple(fields.length);
		tuples[2] = new Tuple(fields.length);
		tuples[0].attributes[0] = 15;
		tuples[1].attributes[0] = 23;
		tuples[2].attributes[0] = 54;

		return new ResultSet(fields, tuples);
	}

	public int executeUpdate(String sql) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void close() throws SQLException {
		// do nothing
	}

	public int getMaxFieldSize() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setMaxFieldSize(int max) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getMaxRows() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setMaxRows(int max) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getQueryTimeout() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void cancel() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void clearWarnings() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setCursorName(String name) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean execute(String sql) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public ResultSet getResultSet() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getUpdateCount() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean getMoreResults() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setFetchDirection(int direction) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getFetchDirection() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setFetchSize(int rows) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getFetchSize() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getResultSetConcurrency() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getResultSetType() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void addBatch(String sql) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void clearBatch() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int[] executeBatch() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Connection getConnection() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean getMoreResults(int current) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean execute(String sql, String[] columnNames) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getResultSetHoldability() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isClosed() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setPoolable(boolean poolable) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isPoolable() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not implemented.");
	}
	
}
