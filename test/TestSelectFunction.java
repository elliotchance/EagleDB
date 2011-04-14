import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSelectFunction {

	private String[][] set;

	private static double ALLOWED_DELTA = 0.0000001;

	public TestSelectFunction() {
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
	public void selectFunctionAcos() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select acos(0.45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.acos(0.45), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionAsin() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select asin(0.45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.asin(0.45), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionAtan() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select atan(0.45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.atan(0.45), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionAtan2() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select atan2(0.45, 0.55)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.atan2(0.45, 0.55), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionAbs() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select abs(-45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.abs(-45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select abs(-45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.abs(-45.0), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionCos() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select cos(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.cos(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select cos(45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.cos(45.0), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionSin() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select sin(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.sin(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select sin(45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.sin(45.0), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionTan() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select tan(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.tan(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select tan(45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.tan(45.0), ALLOWED_DELTA);
	}
	
}
