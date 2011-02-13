import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestShowDatabases {

    public TestShowDatabases() {
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
	public void showDatabases() throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.conn.createStatement();
		ResultSet rs = st.executeQuery("show databases");
		//while(rs.next())
		//	System.out.println(rs.getString("database"));
		rs.close();
		st.close();
	}

}
