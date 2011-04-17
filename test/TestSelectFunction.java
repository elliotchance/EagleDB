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

	@Test
	public void selectFunctionLength() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select length('hello')", 1);
		assertEquals(5, (int) Integer.valueOf(set[0][0]));
	}

	@Test
	public void selectFunctionDegrees() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select degrees(5.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.toDegrees(5.0), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionRadians() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select radians(5.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.toRadians(5.0), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionCbrt() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select cbrt(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.cbrt(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select cbrt(45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.cbrt(45.0), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionCeil() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select ceil(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.ceil(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select ceil(45.7)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.ceil(45.7), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionCeiling() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select ceiling(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.ceil(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select ceiling(45.3)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.ceil(45.3), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionDiv() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select div(9, 4)", 1);
		assertEquals(Double.valueOf(set[0][0]), 2.0, ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select div(9.0, 4.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), 2.0, ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionExp() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select exp(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.exp(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select exp(45.3)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.exp(45.3), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionFloor() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select floor(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.floor(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select floor(45.3)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.floor(45.3), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionLn() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select ln(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.log(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select ln(45.3)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.log(45.3), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionLog() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select log(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.log10(45), ALLOWED_DELTA);
		set = TestSuiteEmbeddedDatabase.executeQuery("select log(45.3)", 1);
		assertEquals(Double.valueOf(set[0][0]), Math.log10(45.3), ALLOWED_DELTA);
	}

	@Test
	public void selectFunctionXMLRoot() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select xmlroot('<abc/>')", 1);
		assertEquals("<?xml version=\"1.0\"?>\n<abc/>", set[0][0]);
		set = TestSuiteEmbeddedDatabase.executeQuery("select xmlroot('<abc/>', version '1.1')", 1);
		assertEquals("<?xml version=\"1.1\"?>\n<abc/>", set[0][0]);
		set = TestSuiteEmbeddedDatabase.executeQuery("select xmlroot('<abc/>', version '1.1', standalone yes)", 1);
		assertEquals("<?xml version=\"1.1\" standalone=\"yes\"?>\n<abc/>", set[0][0]);
		set = TestSuiteEmbeddedDatabase.executeQuery("select xmlroot('<abc/>', version '1.1', standalone no)", 1);
		assertEquals("<?xml version=\"1.1\" standalone=\"no\"?>\n<abc/>", set[0][0]);
	}
	
}
