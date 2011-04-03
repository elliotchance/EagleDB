

import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestExplain {

    public TestExplain() {
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
	public void explain() throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.connection.createStatement();

		/*ResultSet rs = st.executeQuery("explain " + Select.selectSQL);
		System.out.println("EXPLAIN " + Select.selectSQL);
		while(rs.next())
			System.out.println("  " + rs.getString(0));
		System.out.println();
		rs.close();

		rs = st.executeQuery("explain analyze " + Select.selectSQL);
		System.out.println("1: EXPLAIN ANALYZE " + Select.selectSQL);
		while(rs.next())
			System.out.println("  " + rs.getString(0));
		System.out.println();
		rs.close();

		rs = st.executeQuery("explain analyze select id, number from mytable where id>10 or id<20");
		System.out.println("2: EXPLAIN ANALYZE " + Select.selectSQL);
		while(rs.next())
			System.out.println("  " + rs.getString(0));
		System.out.println();
		rs.close();*/

		st.close();
	}

}
