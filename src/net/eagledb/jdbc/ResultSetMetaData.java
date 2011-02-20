package net.eagledb.jdbc;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {
	
	private net.eagledb.jdbc.ResultSet resultSet = null;

	public ResultSetMetaData(net.eagledb.jdbc.ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public String getCatalogName(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getColumnClassName(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getColumnCount() throws SQLException {
		return resultSet.fields.length;
	}

	public int getColumnDisplaySize(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getColumnLabel(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getColumnName(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getColumnType(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getColumnTypeName(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getPrecision(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getScale(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getSchemaName(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getTableName(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isAutoIncrement(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isCaseSensitive(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isCurrency(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isDefinitelyWritable(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int isNullable(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isReadOnly(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isSearchable(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isSigned(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isWritable(int column) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

}
