import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTransaction {

	private String[][] set;

    public TestTransaction() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
		TestSuiteEmbeddedDatabase.executeUpdate("create table transaction1 (id int)");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		TestSuiteEmbeddedDatabase.executeUpdate("drop table transaction1");
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
	public void transactionIsolation() throws Exception {
		// we need a new connection for this
		Connection conn2 = TestSuiteEmbeddedDatabase.newConnection(TestSuiteEmbeddedDatabase.databaseName);
		conn2.setAutoCommit(false);

		// insert into connection2
		TestSuiteEmbeddedDatabase.executeUpdate("begin", conn2);
		TestSuiteEmbeddedDatabase.executeUpdate("insert into transaction1 (id) values (1)", conn2);

		// connection1 sees 0 records
		set = TestSuiteEmbeddedDatabase.executeQuery("select id from transaction1", 1);
		assertArrayEquals(new String[][] {
		}, set);

		// commit connection2
		conn2.commit();

		// connection1 now sees 1 record since its now committed
		set = TestSuiteEmbeddedDatabase.executeQuery("select id from transaction1", 1);
		assertArrayEquals(new String[][] {
			{ "1" },
		}, set);
	}

}
