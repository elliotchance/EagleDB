package net.eagledb.jdbc;

import java.sql.*;
import java.util.*;

public class Driver implements java.sql.Driver {

	/**
	 * Register driver.
	 */
	static {
		try {
			DriverManager.registerDriver(new Driver());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	};

	/**
	 * Retrieves whether the driver thinks that it can open a connection to the given URL. Typically drivers will return
	 * true if they understand the subprotocol specified in the URL and false if they do not.
	 * @param url the URL of the database
	 * @returns true if this driver understands the given URL; false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean acceptsURL(String url) throws SQLException {
		System.out.print("TESTING: " + url);
		return url.startsWith("eagledb://");
	}

	/**
	 * Gets information about the possible properties for this driver.
	 *
	 * The getPropertyInfo method is intended to allow a generic GUI tool to discover what properties it should prompt a
	 * human for in order to get enough information to connect to a database. Note that depending on the values the
	 * human has supplied so far, additional values may become necessary, so it may be necessary to iterate though
	 * several calls to the getPropertyInfo method.
	 *
	 * @param url the URL of the database to which to connect info - a proposed list of tag/value pairs that will be
	 *            sent on connect open
	 * @param info
	 * @return an array of DriverPropertyInfo objects describing possible properties. This array may be an empty array
	 *         if no properties are required.
	 * @throws SQLException if a database access error occurs
	 */
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return null;
	}

	/**
	 * Retrieves the driver's major version number. Initially this should be 1.
	 * @return this driver's major version number
	 */
	public int getMajorVersion() {
		return net.eagledb.server.Server.MAJOR_VERSION;
	}

	/**
	 * Gets the driver's minor version number. Initially this should be 0.
	 * @return this driver's minor version number
	 */
	public int getMinorVersion() {
		return net.eagledb.server.Server.MINOR_VERSION;
	}

	/**
	 * Reports whether this driver is a genuine JDBC CompliantTM driver. A driver may only report true here if it passes
	 * the JDBC compliance tests; otherwise it is required to return false. JDBC compliance requires full support for
	 * the JDBC API and full support for SQL 92 Entry Level. It is expected that JDBC compliant drivers will be
	 * available for all the major commercial databases.
	 *
	 * This method is not intended to encourage the development of non-JDBC compliant drivers, but is a recognition of
	 * the fact that some vendors are interested in using the JDBC API and framework for lightweight databases that do
	 * not support full database functionality, or for special databases such as document information retrieval where a
	 * SQL implementation may not be feasible.
	 *
	 * @return true if this driver is JDBC Compliant; false otherwise
	 */
	public boolean jdbcCompliant() {
		return true;
	}

	/**
	 * Attempts to make a database connection to the given URL. The driver should return "null" if it realizes it is the
	 * wrong kind of driver to connect to the given URL. This will be common, as when the JDBC driver manager is asked
	 * to connect to a given URL it passes the URL to each loaded driver in turn.
	 * 
	 * The driver should throw an SQLException if it is the right driver to connect to the given URL but has trouble
	 * connecting to the database.
	 * 
	 * The java.util.Properties argument can be used to pass arbitrary string tag/value pairs as connection arguments.
	 * Normally at least "user" and "password" properties should be included in the Properties object.
	 * 
	 * @param url the URL of the database to which to connect
	 * @param info a list of arbitrary string tag/value pairs as connection arguments. Normally at least a "user" and
	 *             "password" property should be included.
	 * @return a Connection object that represents a connection to the URL
	 * @throws SQLException SQLException - if a database access error occurs
	 */
	public java.sql.Connection connect(String url, Properties info) throws SQLException {
		return new net.eagledb.jdbc.Connection(url, info);
	}

}
