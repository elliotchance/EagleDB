package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.show.databases.ShowDatabases;
import net.eagledb.server.*;
import net.eagledb.server.storage.*;
import java.sql.*;

public class SQLShowDatabases implements SQLAction {
	
	private ShowDatabases sql;

	public SQLShowDatabases(ShowDatabases sql) {
		this.sql = sql;
		System.out.println(sql);
	}

	public Result getResult() throws SQLException {
		// setup column definitions
		Field[] fields = new Field[1];
		fields[0] = new Field("database", IntPage.class);

		// add tuples
		Tuple[] tuples = new Tuple[3];
		tuples[0] = new Tuple(fields.length);
		tuples[1] = new Tuple(fields.length);
		tuples[2] = new Tuple(fields.length);
		tuples[0].attributes[0] = 15;
		tuples[1].attributes[0] = 23;
		tuples[2].attributes[0] = 54;

		return new Result(fields, tuples);
	}

}
