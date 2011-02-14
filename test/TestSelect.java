import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSelect {

    public TestSelect() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
		// create the temporary table used for these tests
		TestSuiteEmbeddedDatabase.executeUpdate("create temp table test1 (id int, number real)");

		// insert some test records
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test1 (id, number) values (1, 1.0)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test1 (id, number) values (2, 2.5)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test1 (id, number) values (3, 8.7)");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    @Test
	public void select() throws Exception {
		String[][] set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1", 2);
		assertEquals(3, set.length);
		assertArrayEquals(new String[][] {
			{ "1", "1.0" },
			{ "2", "2.5" },
			{ "3", "8.7" },
		}, set);
	}

}
