import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import net.eagledb.server.EmbeddedServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	// required
	TestCreateDatabase.class,
	TestConnect.class,

	// databases
	TestShowDatabases.class,

	// tables
	TestCreateTable.class,
	TestDropTable.class,
	
	// INSERT
	TestInsert.class,

	// SELECT
	TestExplain.class,
	TestSelect.class,

	// transactions
	TestTransaction.class,

	// INDEX
	TestIndex.class,

	// required
	TestDropDatabase.class,
	TestDisconnect.class
})
public class TestSuiteEmbeddedDatabase {

	public static EmbeddedServer server = null;

	public static Connection connection = null;

	public static String databaseName;

	public static String databaseUser = "root";

	public static String databasePassword = "123";

	public static String connectionURL = "eagledb://localhost";

	@BeforeClass
	public static void setUpClass() throws Exception {
		// start the server
		server = new EmbeddedServer();

		// generate a random database name
		databaseName = new BigInteger(130, new SecureRandom()).toString(32);

		// client connection without database
		Class.forName("net.eagledb.jdbc.Driver");
		connection = newConnection();
	}

	public static Connection newConnection() throws SQLException {
		return DriverManager.getConnection(connectionURL, databaseUser, databasePassword);
	}

	public static Connection newConnection(String database) throws SQLException {
		return DriverManager.getConnection(connectionURL + "/" + database, databaseUser, databasePassword);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		server.stop();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public static void executeUpdate(String sql) throws SQLException {
		executeUpdate(sql, TestSuiteEmbeddedDatabase.connection);
	}

	public static void executeUpdate(String sql, Connection conn) throws SQLException {
		if(conn == null)
			throw new SQLException("Not a valid connection");
		
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
	}

	public static String[][] executeQuery(String sql, int columns, Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);

		ArrayList<String[]> tuples = new ArrayList<String[]>();
		while(rs.next()) {
			String[] tuple = new String[columns];
			for(int i = 1; i <= columns; ++i)
				tuple[i - 1] = rs.getString(i);
			tuples.add(tuple);
		}
		rs.close();
		st.close();

		String[][] r = new String[tuples.size()][columns];
		for(int i = 0; i < tuples.size(); ++i) {
			for(int j = 1; j <= columns; ++j)
				r[i][j - 1] = tuples.get(i)[j - 1];
		}
		return r;
	}

	public static String[][] executeQuery(String sql, int columns) throws SQLException {
		return executeQuery(sql, columns, TestSuiteEmbeddedDatabase.connection);
	}

}
