import java.sql.Connection;
import java.sql.DriverManager;
import net.eagledb.server.EmbeddedServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestCreateDatabase.class,
	TestCreateTable.class,
	TestExplain.class,
	TestInsert.class,
	TestSelect.class,
	TestShowDatabases.class
})
public class TestSuiteEmbeddedDatabase {

	public static EmbeddedServer server = null;

	public static Connection conn = null;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// start the server
		server = new EmbeddedServer();

		// client connection
		Class.forName("net.eagledb.jdbc.Driver");
		conn = DriverManager.getConnection("eagledb://localhost/eagledb", "root", "123");
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

}
