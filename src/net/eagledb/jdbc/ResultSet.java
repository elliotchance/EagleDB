package net.eagledb.jdbc;

import java.sql.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.net.*;
import net.eagledb.server.storage.*;

public class ResultSet implements java.sql.ResultSet {
	
	private Field[] fields;
	
	private Tuple[] tuples;

	private int cursorPosition = -1;

	public ResultSet(Field[] theFields, Tuple[] theTuples) {
		fields = theFields;
		tuples = theTuples;
	}

	public boolean absolute(int row) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void afterLast() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void beforeFirst() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void cancelRowUpdates() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void clearWarnings() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void close() throws SQLException {
		// do nothing
	}

	public void deleteRow() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int findColumn(String columnLabel) throws SQLException {
		int i = 0;
		for(Field f : fields) {
			if(f.name.equals(columnLabel))
				return i;
			++i;
		}

		throw new SQLException("No such field '" + columnLabel + "'");
	}

	public boolean first() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Array getArray(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Array getArray(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Blob getBlob(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Blob getBlob(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public byte getByte(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public byte getByte(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Clob getClob(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Clob getClob(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getConcurrency() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public String getCursorName() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public java.sql.Date getDate(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public java.sql.Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public java.sql.Date getDate(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public java.sql.Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public double getDouble(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public double getDouble(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getFetchDirection() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getFetchSize() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public float getFloat(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public float getFloat(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getHoldability() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getInt(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getInt(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public long getLong(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public long getLong(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public String getNString(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public String getNString(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Object getObject(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Object getObject(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Ref getRef(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Ref getRef(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getRow() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public short getShort(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public short getShort(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Statement getStatement() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public String getString(int columnIndex) throws SQLException {
		return tuples[cursorPosition].attributes[columnIndex].toString();
	}

	public String getString(String columnLabel) throws SQLException {
		return tuples[cursorPosition].attributes[findColumn(columnLabel)].toString();
	}

	public Time getTime(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Time getTime(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public int getType() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public URL getURL(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public URL getURL(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void insertRow() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isAfterLast() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isBeforeFirst() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isClosed() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isFirst() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isLast() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean last() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void moveToCurrentRow() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void moveToInsertRow() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean next() throws SQLException {
		if(cursorPosition > tuples.length - 2)
			return false;

		++cursorPosition;
		return true;
	}

	public boolean previous() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void refreshRow() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean relative(int rows) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean rowDeleted() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean rowInserted() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean rowUpdated() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setFetchDirection(int direction) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void setFetchSize(int rows) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateDate(int columnIndex, java.sql.Date x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateDate(String columnLabel, java.sql.Date x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNull(int columnIndex) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateNull(String columnLabel) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateRow() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateString(String columnLabel, String x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean wasNull() throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not implemented.");
	}
}
