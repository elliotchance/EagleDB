import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBlock {

	private String[][] set;

	private static double ALLOWED_DELTA = 0.0000001;

	public TestBlock() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		TestSuiteEmbeddedDatabase.executeUpdate("create temp table block_result (message int)");
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
	public void blockAnonymous() throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.connection.createStatement();

		String sql =
			  "BEGIN\n"
			+ "  INSERT INTO block_result (message) VALUES (456);\n"
			+ "  INSERT INTO block_result (message) VALUES (123);\n"
			+ "END;";
		st.executeUpdate(sql);
		st.close();

		set = TestSuiteEmbeddedDatabase.executeQuery("SELECT message FROM block_result", 1);
		assertArrayEquals(new String[][]{
				{"456"},
				{"123"},}, set);

	}
	
}
