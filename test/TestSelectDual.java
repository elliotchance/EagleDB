import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSelectDual {

	private String[][] set;

	public TestSelectDual() {
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
	public void selectDualAdd() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select 2+3", 1);
		assertArrayEquals(new String[][]{{"5"}}, set);
	}
	
}