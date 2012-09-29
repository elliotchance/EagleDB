import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUpdate {

	private String[][] set;

    public TestUpdate() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
		// create the temporary table used for these tests
		TestSuiteEmbeddedDatabase.executeUpdate("create table test_update (id int, number double)");

		// insert some test records
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_update (id, number) values (1, 1.0)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_update (id, number) values (2, 2.5)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_update (id, number) values (3, 8.7)");
    }

    @After
    public void tearDown() throws Exception {
		TestSuiteEmbeddedDatabase.executeUpdate("drop table test_update");
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
