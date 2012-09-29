package net.eagledb.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import net.eagledb.server.storage.Attribute;
import net.eagledb.server.storage.Tuple;

public class ResultSet implements java.sql.ResultSet {
	
	/**
	 * Field definitions.
	 */
	public Attribute[] fields;
	
	/**
	 * Tuple data.
	 */
	private Tuple[] tuples;

	/**
	 * The cursor position indicates the currently active row. The initial value is zero which is one before the first
	 * row.
	 */
	private int cursorPosition = 0;

	/**
	 * Create a result set.
	 * @param theFields Field definitions.
	 * @param theTuples Tuples for the result set.
	 */
	public ResultSet(Attribute[] theFields, Tuple[] theTuples) {
		fields = theFields;
		tuples = theTuples;
	}

	/**
	 * Moves the cursor to the given row number in this ResultSet object.
	 *
	 * If the row number is positive, the cursor moves to the given row number with respect to the beginning of the
	 * result set. The first row is row 1, the second is row 2, and so on.
	 *
	 * If the given row number is negative, the cursor moves to an absolute row position with respect to the end of the
	 * result set. For example, calling the method absolute(-1) positions the cursor on the last row; calling the method
	 * absolute(-2) moves the cursor to the next-to-last row, and so on.
	 *
	 * An attempt to position the cursor beyond the first/last row in the result set leaves the cursor before the first
	 * row or after the last row.
	 *
	 * @param row
	 * @return
	 * @throws SQLException
	 */
	public boolean absolute(int row) throws SQLException {
		// try to move cursor
		if(row < 0) {
			cursorPosition = tuples.length - row;
		}
		cursorPosition = row;

		// check validity
		if(cursorPosition < 0) {
			cursorPosition = 0;
			return false;
		}
		else if(cursorPosition > tuples.length) {
			cursorPosition = tuples.length;
			return false;
		}

		return true;
	}

	public void afterLast() throws SQLException {
		cursorPosition = tuples.length + 1;
	}

	public void beforeFirst() throws SQLException {
		cursorPosition = 0;
	}

	public void cancelRowUpdates() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void clearWarnings() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void close() throws SQLException {
		// do nothing
	}

	public void deleteRow() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int findColumn(String columnLabel) throws SQLException {
		int i = 0;
		String s = "(";
		for(Attribute f : fields) {
			if(f.getName().equals(columnLabel)) {
				return i;
			}
			if(i > 0) {
				s += ",";
			}
			s += f.getName();
			++i;
		}
		s += ")";

		throw new SQLException("No such field '" + columnLabel + "' in " + s);
	}

	public boolean first() throws SQLException {
		return absolute(1);
	}

	public Array getArray(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Array getArray(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Blob getBlob(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Blob getBlob(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public byte getByte(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public byte getByte(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		return getString(columnIndex).getBytes();
	}

	public byte[] getBytes(String columnLabel) throws SQLException {
		return getString(columnLabel).getBytes();
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Clob getClob(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Clob getClob(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getConcurrency() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getCursorName() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public java.sql.Date getDate(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public java.sql.Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public java.sql.Date getDate(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public java.sql.Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public double getDouble(int columnIndex) throws SQLException {
		return Double.valueOf(tuples[cursorPosition - 1].get(columnIndex).toString());
	}

	public double getDouble(String columnLabel) throws SQLException {
		return Double.valueOf(tuples[cursorPosition - 1].get(findColumn(columnLabel)).toString());
	}

	public int getFetchDirection() throws SQLException {
		return ResultSet.FETCH_FORWARD;
	}

	public int getFetchSize() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public float getFloat(int columnIndex) throws SQLException {
		return Float.valueOf(tuples[cursorPosition - 1].get(columnIndex).toString());
	}

	public float getFloat(String columnLabel) throws SQLException {
		return getFloat(findColumn(columnLabel));
	}

	public int getHoldability() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getInt(int columnIndex) throws SQLException {
		return (int) (double) Double.valueOf(tuples[cursorPosition - 1].get(columnIndex - 1).toString());
	}

	public int getInt(String columnLabel) throws SQLException {
		return getInt(findColumn(columnLabel) + 1);
	}

	public long getLong(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public long getLong(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return new net.eagledb.jdbc.ResultSetMetaData(this);
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getNString(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getNString(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Object getObject(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Object getObject(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Ref getRef(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Ref getRef(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getRow() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public short getShort(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public short getShort(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Statement getStatement() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public String getString(int columnIndex) throws SQLException {
		validateColumnIndex(columnIndex);
		return tuples[cursorPosition - 1].get(columnIndex - 1).toString();
	}

	public String getString(String columnLabel) throws SQLException {
		int columnIndex = findColumn(columnLabel);
		return tuples[cursorPosition - 1].get(columnIndex).toString();
	}

	public Time getTime(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Time getTime(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public int getType() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public URL getURL(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public URL getURL(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void insertRow() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isAfterLast() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isBeforeFirst() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isClosed() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isFirst() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isLast() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean last() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void moveToCurrentRow() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void moveToInsertRow() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean next() throws SQLException {
		if(cursorPosition >= tuples.length) {
			return false;
		}

		++cursorPosition;
		return true;
	}

	public boolean previous() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void refreshRow() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean relative(int rows) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean rowDeleted() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean rowInserted() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean rowUpdated() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setFetchDirection(int direction) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setFetchSize(int rows) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateDate(int columnIndex, java.sql.Date x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateDate(String columnLabel, java.sql.Date x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNull(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateNull(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateRow() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateString(String columnLabel, String x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean wasNull() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	/**
	 * Make sure columnIndex is in an appropriate range.
	 * @param columnIndex The column to test.
	 * @throws SQLException If the columnIndex is out of range.
	 */
	protected void validateColumnIndex(int columnIndex) throws SQLException {
		if(columnIndex < 1 || columnIndex > tuples[cursorPosition - 1].size()) {
			throw new SQLException("columnIndex out of bounds: " + columnIndex);
		}
	}

}
