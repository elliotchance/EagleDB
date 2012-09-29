package bench;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import net.eagledb.server.EmbeddedServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TPCCBenchmark {

	public TPCCBenchmark() {
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
	public void run() throws SQLException, ClassNotFoundException {
		// limits
		int customers = 1000;
		int transactions = 1000;
		
		// start the server
		EmbeddedServer server = new EmbeddedServer();
		
		// connect
		Class.forName("net.eagledb.jdbc.Driver");
		Connection conn = DriverManager.getConnection("eagledb://localhost", "root", "123");
		
		// create database
		Statement st = conn.createStatement();
		st.executeUpdate("CREATE DATABASE tpcc");
		st.close();
		conn = DriverManager.getConnection("eagledb://localhost/tpcc", "root", "123");
		
		// load data
		st = conn.createStatement();
		st.executeUpdate("CREATE TABLE customer (id int, balance double)");
		for(int i = 1; i <= customers; ++i) {
			st.executeUpdate("insert into customer (id, balance) values (" + i + ", " + (Math.floor(Math.random() * 10000) / 100) + ")");
		}
		st.close();
		
		// run
		long start = Calendar.getInstance().getTimeInMillis();
		for(int i = 1; i <= 100000; ++i) {
			st = conn.createStatement();
			double rand = Math.random();
			
			// 50% chance lookup balance
			//if(rand < 0.5)
				st.executeQuery("select balance from customer where id=" + i);
			
			// 10% chance to move cash between accounts
			//st.executeQuery("begin " +
			//	"update ");
			//while(rs.next())
			//	System.out.println(rs.getString("balance"));
			st.close();
		}
		System.out.println("Time: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		
		// finish
		st = conn.createStatement();
		st.executeUpdate("DROP DATABASE tpcc");
		st.close();
	}
}
