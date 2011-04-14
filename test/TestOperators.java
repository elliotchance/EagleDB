import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestOperators {

	private String[][] set;

	private static double ALLOWED_DELTA = 0.0000001;

	public TestOperators() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
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
	public void operatorAdd() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2+3", 1);
		assertEquals(5.0, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2.1+3", 1);
		assertEquals(5.1, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2+3.2", 1);
		assertEquals(5.2, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2.1+3.2", 1);
		assertEquals(5.3, Double.valueOf(set[0][0]), ALLOWED_DELTA);
	}

	@Test
	public void operatorSubtract() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2-3", 1);
		assertEquals(-1.0, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2.1-3", 1);
		assertEquals(-0.9, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2-3.2", 1);
		assertEquals(-1.2, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2.1-3.2", 1);
		assertEquals(-1.1, Double.valueOf(set[0][0]), ALLOWED_DELTA);
	}

	@Test
	public void operatorMultiply() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2*3", 1);
		assertEquals(6.0, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2.1*3", 1);
		assertEquals(6.3, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2*3.2", 1);
		assertEquals(6.4, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2.1*3.2", 1);
		assertEquals(6.72, Double.valueOf(set[0][0]), ALLOWED_DELTA);
	}

	@Test
	public void operatorDivide() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2/3", 1);
		assertEquals(0.6666666666666666, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2.1/3", 1);
		assertEquals(0.7, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2/3.2", 1);
		assertEquals(0.625, Double.valueOf(set[0][0]), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2.1/3.2", 1);
		assertEquals(0.65625, Double.valueOf(set[0][0]), ALLOWED_DELTA);
	}
	
}
