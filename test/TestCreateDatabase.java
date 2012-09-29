

import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCreateDatabase {

    public TestCreateDatabase() {
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
	public void createDatabase() throws Exception {
		TestSuiteEmbeddedDatabase.executeUpdate("create database \"" + TestSuiteEmbeddedDatabase.databaseName + "\"");
	}

    @Test
	public void createDatabaseExists() throws Exception {
		try {
			TestSuiteEmbeddedDatabase.executeUpdate("create database \"" + TestSuiteEmbeddedDatabase.databaseName + "\"");
		}
		catch(SQLException e) {
			if(e.getMessage().indexOf("already exists") < 0) {
				throw e;
			}
			return;
		}

		throw new Exception("Failed: SQLException was never thrown");
	}

}
