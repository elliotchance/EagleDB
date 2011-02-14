import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDropDatabase {

    public TestDropDatabase() {
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
	public void dropDatabase() throws Exception {
		TestSuiteEmbeddedDatabase.executeUpdate("drop database " + TestSuiteEmbeddedDatabase.databaseName);
	}

}
