import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDropTable {

    public TestDropTable() {
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
	public void dropTable() throws Exception {
		TestSuiteEmbeddedDatabase.executeUpdate("create table droptable (id int)");
		TestSuiteEmbeddedDatabase.executeUpdate("drop table droptable");
	}

}
