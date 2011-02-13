package net.eagledb.test.compare;

import java.sql.SQLException;

public interface Benchmark {

	public static String createTableSQL = "create table mytable (id int, number real)";

	public static int testTuples = 200000;

	public void init() throws SQLException;

	public void createTable() throws SQLException;

	public void insert() throws SQLException;

	public void select(String selectSQL) throws SQLException;

	public void end() throws SQLException;

}
