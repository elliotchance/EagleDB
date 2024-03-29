

import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCreateTable {

    public TestCreateTable() {
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
	public void createTable() throws Exception {
		try {
			TestSuiteEmbeddedDatabase.executeUpdate("create table mytable (id int, number real)");
		}
		catch(SQLException e) {
			if(e.getMessage().indexOf("already exists") < 0) {
				throw e;
			}
		}
	}

    @Test
	public void createTemporaryTable() throws Exception {
		TestSuiteEmbeddedDatabase.executeUpdate("create temporary table mytemp (id int, number real)");
	}

}
