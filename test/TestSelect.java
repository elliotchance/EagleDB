import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSelect {

	public static String selectSQL = "select id, number from mytable where id>10 and id<20";

    public TestSelect() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() throws Exception {
		// create the temporary table used for these tests
		executeQuery("create temp table test1 (id int, number double)");
    }

    @After
    public void tearDown() {
    }

	private void executeQuery(String sql) throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.close();
		st.close();
	}

    @Test
	public void select() throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.conn.createStatement();
		ResultSet rs = st.executeQuery(selectSQL);
		//while(rs.next())
		//	System.out.println(rs.getInt(0) + " | " + rs.getFloat(1));
		rs.close();
		st.close();
	}

}
