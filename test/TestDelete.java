
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDelete {

	private String[][] set;

	public TestDelete() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() throws SQLException {
		// create the temporary tables used for these tests
		TestSuiteEmbeddedDatabase.executeUpdate("create temp table delete1 (id int, number real)");
		TestSuiteEmbeddedDatabase.executeUpdate("create temp table delete2 (id int, number real)");
	}

	@After
	public void tearDown() {
	}

	@Test
	public void delete() throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.connection.createStatement();

		// insert a bunch of records
		long start = Calendar.getInstance().getTimeInMillis();
		int tuples = 5;
		for (int i = 1; i <= tuples; ++i)
			TestSuiteEmbeddedDatabase.executeUpdate("insert into delete1 (id, number) values ("
				+ i + ", " + Math.sqrt(i) + ")");

		// delete a record (autocommit is on)
		TestSuiteEmbeddedDatabase.executeUpdate("delete from delete1 where id=2");

		// count visible rows
		set = TestSuiteEmbeddedDatabase.executeQuery("select id from delete1", 1);
		assertArrayEquals(new String[][]{
				{"1"}, {"3"}, {"4"}, {"5"},}, set);

		st.close();
	}

	@Test
	public void deleteLimit() throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.connection.createStatement();

		// insert a bunch of records
		long start = Calendar.getInstance().getTimeInMillis();
		int tuples = 5;
		for (int i = 1; i <= tuples; ++i)
			TestSuiteEmbeddedDatabase.executeUpdate("insert into delete2 (id, number) values ("
				+ i + ", " + Math.sqrt(i) + ")");

		// delete a record (autocommit is on)
		TestSuiteEmbeddedDatabase.executeUpdate("delete from delete2 where id>0 limit 2");

		// count visible rows
		set = TestSuiteEmbeddedDatabase.executeQuery("select id from delete2", 1);
		assertArrayEquals(new String[][]{
				{"3"}, {"4"}, {"5"},}, set);

		st.close();
	}

}
