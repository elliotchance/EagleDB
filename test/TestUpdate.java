import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestUpdate {

	private String[][] set;

    public TestUpdate() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
		// create the temporary table used for these tests
		TestSuiteEmbeddedDatabase.executeUpdate("create temp table test_update (id int, number double)");

		// insert some test records
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_update (id, number) values (1, 1.0)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_update (id, number) values (2, 2.5)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_update (id, number) values (3, 8.7)");
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
    public void updateSingle() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test_update where id=2", 2);
		assertArrayEquals(new String[][]{{"2", "2.5"}}, set);
		
		TestSuiteEmbeddedDatabase.executeUpdate("update test_update set number=7.8/2 where id=2");
		
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test_update where id=2", 2);
		assertArrayEquals(new String[][]{{"2", "3.9"}}, set);
    }
    
}
