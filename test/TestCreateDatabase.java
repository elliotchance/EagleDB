import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		try {
			Statement st = TestSuiteEmbeddedDatabase.conn.createStatement();
			ResultSet rs = st.executeQuery("create database mydb");
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			if(e.getMessage().indexOf("already exists") < 0)
				throw e;
		}
	}

}
