
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSelect {

	private String[][] set;

	public TestSelect() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		// create the temporary table used for these tests
		TestSuiteEmbeddedDatabase.executeUpdate("create temp table test1 (id int, number double)");

		// insert some test records
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test1 (id, number) values (1, 1.0)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test1 (id, number) values (2, 2.5)");
		TestSuiteEmbeddedDatabase.executeUpdate("insert into test1 (id, number) values (3, 8.7)");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void selectLimit() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 limit 2", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},
				{"2", "2.5"},}, set);
	}

	@Test
	public void selectLimitOffset() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 limit 2, 1", 2);
		assertArrayEquals(new String[][]{
				{"3", "8.7"},}, set);

		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 limit 2 offset 1", 2);
		assertArrayEquals(new String[][]{
				{"2", "2.5"},
				{"3", "8.7"},}, set);
	}

	@Test
	public void selectAll() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},
				{"2", "2.5"},
				{"3", "8.7"},}, set);

		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where 1", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},
				{"2", "2.5"},
				{"3", "8.7"},}, set);
	}

	@Test
	public void selectEqual() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id=2", 2);
		assertArrayEquals(new String[][]{
				{"2", "2.5"},}, set);
	}

	@Test
	public void selectNotEqual() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id!=2", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},
				{"3", "8.7"},}, set);

		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id<>2", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},
				{"3", "8.7"},}, set);
	}

	@Test
	public void selectGreaterThan() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id>2", 2);
		assertArrayEquals(new String[][]{
				{"3", "8.7"},}, set);
	}

	@Test
	public void selectGreaterThanEqual() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id>=2", 2);
		assertArrayEquals(new String[][]{
				{"2", "2.5"},
				{"3", "8.7"},}, set);
	}

	@Test
	public void selectLessThan() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id<2", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},}, set);
	}

	@Test
	public void selectLessThanEqual() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id<=2", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},
				{"2", "2.5"},}, set);
	}

	@Test
	public void selectAdd() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id+1=2", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},}, set);
	}

	@Test
	public void selectSubtract() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id-1=2", 2);
		assertArrayEquals(new String[][]{
				{"3", "8.7"},}, set);
	}

	@Test
	public void selectMultiply() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id*2=2", 2);
		assertArrayEquals(new String[][]{
				{"1", "1.0"},}, set);
	}

	@Test
	public void selectAlias() throws Exception {
		Statement s = TestSuiteEmbeddedDatabase.connection.createStatement();
		ResultSet rs = s.executeQuery("select number as f1, number from test1 limit 1");
		rs.next();
		assertEquals(rs.getInt("f1"), 1);
		assertEquals(rs.getFloat("number"), 1.0, 0);
	}

	@Test
	public void selectDivide() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id, number from test1 where id/2=1", 2);
		assertArrayEquals(new String[][]{
				{"2", "2.5"},}, set);
	}

	@Test
	public void selectExpession() throws Exception {
		set = TestSuiteEmbeddedDatabase.executeQuery("select id + 5, number + 2.5 from test1", 2);
		assertArrayEquals(new String[][]{
				{"6", "3.5"},
				{"7", "5.0"},
				{"8", "11.2"}}, set);
	}
	
}
