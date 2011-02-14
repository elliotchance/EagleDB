import java.sql.DriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestConnect {

    public TestConnect() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
	public void connect() throws Exception {
		if(TestSuiteEmbeddedDatabase.conn != null)
			TestSuiteEmbeddedDatabase.conn.close();
		
		TestSuiteEmbeddedDatabase.conn = DriverManager.getConnection("eagledb://localhost/" +
			TestSuiteEmbeddedDatabase.databaseName, "root", "123");
	}

}
