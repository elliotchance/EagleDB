import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestOrderBy {

	private String[][] set;

	public TestOrderBy() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		// create the temporary table used for these tests
		TestSuiteEmbeddedDatabase.executeUpdate("create temp table test_order (id int, number double)");

		// insert some test records
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_order (id, number) values (1, 1.0)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_order (id, number) values (2, 8.7)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test_order (id, number) values (3, 2.5)");
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
	public void orderByExplain() throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.connection.createStatement();

		String sql = "select number from test_order order by number desc";
		ResultSet rs = st.executeQuery("explain " + sql);
		System.out.println("EXPLAIN " + sql);
		while(rs.next())
			System.out.println("  " + rs.getString(1));
		System.out.println();
		rs.close();

		st.close();
	}

	@Test
	public void orderByDouble() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select number from test_order order by number", 1);
		assertArrayEquals(new String[][]{{"1.0"},{"2.5"},{"8.7"}}, set);
	}

	@Test
	public void orderByDoubleDesc() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select number from test_order order by number desc", 1);
		assertArrayEquals(new String[][]{{"8.7"},{"2.5"},{"1.0"}}, set);
	}
	
}
