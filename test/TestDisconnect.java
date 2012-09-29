

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDisconnect {

    public TestDisconnect() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
	public void disconnect() throws Exception {
		if(TestSuiteEmbeddedDatabase.connection != null) {
			TestSuiteEmbeddedDatabase.connection.close();
		}
	}

}
