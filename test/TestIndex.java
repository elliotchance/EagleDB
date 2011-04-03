

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestIndex {

	private String[][] set;

    public TestIndex() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() throws SQLException {
		TestSuiteEmbeddedDatabase.executeUpdate("create temporary table index1 (id int, number double)");

		for(int i = 1; i <= 100; ++i)
			TestSuiteEmbeddedDatabase.executeUpdate("insert into index1 (id, number) values (" + (i * i) + ", " +
				Math.sqrt(i) + ")");
    }

    @After
    public void tearDown() {
    }

    @Test
	public void createIndex() throws Exception {
		Statement st;
		ResultSet rs;

		// explain without index
		st = TestSuiteEmbeddedDatabase.connection.createStatement();
		rs = st.executeQuery("explain select id, number from index1 where id=16");
		while(rs.next())
			System.out.println(rs.getString(1));
		System.out.println();
		rs.close();
		st.close();

		// without index
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from index1 where id=16", 2);
		assertArrayEquals(new String[][] {
			{ "16", "2.0" },
		}, set);

		// create index
		TestSuiteEmbeddedDatabase.executeUpdate("create index index1_id on index1(id)");

		// explain with index
		st = TestSuiteEmbeddedDatabase.connection.createStatement();
		rs = st.executeQuery("explain select id, number from index1 where id=16");
		while(rs.next())
			System.out.println(rs.getString(1));
		System.out.println();
		rs.close();
		st.close();

		// with index
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from index1 where id=16", 2);
		assertArrayEquals(new String[][] {
			{ "16", "2.0" },
		}, set);
	}

}
