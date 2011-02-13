package net.eagledb.test.compare;

import java.sql.SQLException;
import java.util.Calendar;

public class Run {

	public static void main(String[] args) {
		new Run();
	}

	public static long getNow() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public void printTime(String name, long start) {
		long time = Run.getNow() - start;
		System.out.println("  " + name + ": " + time + "ms (" + (int) (Benchmark.testTuples / (time / 1000.0)) +
			" tuples/s)");
	}

	public Run() {
		Benchmark[] benchmarks = new Benchmark[] {
			new EagleDB(),
			new Derby(),
			new HSQLDB(),
			new H2()
		};

		System.out.println("Test Parameters:");
		System.out.println("  Test Tuples: " + Benchmark.testTuples);
		System.out.println("  CREATE TABLE: " + Benchmark.createTableSQL);
		
		for(Benchmark benchmark : benchmarks) {
			long start = 0;
			try {
				System.out.println(benchmark.getClass().getSimpleName() + "...");
				benchmark.init();
				benchmark.createTable();

				start = Run.getNow();
				benchmark.insert();
				printTime("INSERT", start);

				start = Run.getNow();
				benchmark.select("select id, number from mytable where id>" + (Benchmark.testTuples - 10));
				printTime("SELECT", start);

				start = Run.getNow();
				benchmark.select("select id, number from mytable where id>" + (Benchmark.testTuples - 9));
				printTime("SELECT", start);

				start = Run.getNow();
				benchmark.select("select id, number from mytable where id>" + (Benchmark.testTuples - 8));
				printTime("SELECT", start);

				benchmark.end();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Done");
	}

}
