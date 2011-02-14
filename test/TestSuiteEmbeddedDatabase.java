import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	// databases
	TestCreateDatabase.class,
	TestShowDatabases.class,
	TestConnect.class,

	// tables
	TestCreateTable.class,
	TestDropTable.class,
		
	// INSERT
	TestInsert.class,

	// SELECT
	TestExplain.class,
	TestSelect.class,

	// disconnect
	TestDisconnect.class
})
public class TestSuiteEmbeddedDatabase {

	public static EmbeddedServer server = null;

	public static Connection conn = null;

	public static String databaseName;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// start the server
		server = new EmbeddedServer();

		// generate a random database name
		databaseName = new BigInteger(130, new SecureRandom()).toString(32);

		// client connection without database
		Class.forName("net.eagledb.jdbc.Driver");
		conn = DriverManager.getConnection("eagledb://localhost", "root", "123");
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

	public static void executeUpdate(String sql) throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.conn.createStatement();
		st.executeUpdate(sql);
		st.close();
	}

	public static String[][] executeQuery(String sql, int columns) throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.conn.createStatement();
		ResultSet rs = st.executeQuery(sql);

		ArrayList<String[]> tuples = new ArrayList<String[]>();
		while(rs.next()) {
			String[] tuple = new String[columns];
			for(int i = 0; i < columns; ++i)
				tuple[i] = rs.getString(i);
			tuples.add(tuple);
		}
		rs.close();
		st.close();

		String[][] r = new String[tuples.size()][columns];
		for(int i = 0; i < tuples.size(); ++i) {
			for(int j = 0; j < columns; ++j)
				r[i][j] = tuples.get(i)[j];
		}
		return r;
	}

}
