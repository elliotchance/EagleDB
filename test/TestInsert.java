import java.sql.Statement;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestInsert {

    public TestInsert() {
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
	public void insert() throws Exception {
		Statement st = TestSuiteEmbeddedDatabase.connection.createStatement();

		// insert a bunch of records
		long start = Calendar.getInstance().getTimeInMillis();
		int tuples = 1500;
		for(int i = 0; i < tuples; ++i)
			TestSuiteEmbeddedDatabase.executeUpdate("insert into mytable (id, number) values (" + i + ", " +
				Math.sqrt(i) + ")");
		long time = Calendar.getInstance().getTimeInMillis() - start;
		//System.out.println("INSERT: " + (int) (tuples / (time / 1000.0)) + " per second");

		st.close();
	}

}
