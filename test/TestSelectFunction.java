import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSelectFunction {

	private String[][] set;

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
	public void selectFunctionAbs() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select abs(-45)", 1);
		assertEquals(Double.valueOf(set[0][0]), 45, 0.0);
		set = TestSuiteEmbeddedDatabase.executeQuery("select abs(-45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), 45, 0.0);
	}

	@Test
	public void selectFunctionCos() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select cos(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), 0.5253219888177297, 0.0);
		set = TestSuiteEmbeddedDatabase.executeQuery("select cos(45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), 0.5253219888177297, 0.0);
	}

	@Test
	public void selectFunctionSin() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select sin(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), 0.8509035245341184, 0.0);
		set = TestSuiteEmbeddedDatabase.executeQuery("select sin(45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), 0.8509035245341184, 0.0);
	}

	@Test
	public void selectFunctionTan() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select tan(45)", 1);
		assertEquals(Double.valueOf(set[0][0]), 1.6197751905438615, 0.0);
		set = TestSuiteEmbeddedDatabase.executeQuery("select tan(45.0)", 1);
		assertEquals(Double.valueOf(set[0][0]), 1.6197751905438615, 0.0);
	}
	
}
