package net.eagledb.server.sql;

import net.sf.jsqlparser.statement.show.databases.ShowDatabases;

public class SQLShowDatabases {
	
	private ShowDatabases sql;

	public SQLShowDatabases(ShowDatabases sql) {
		this.sql = sql;
		System.out.println(sql);
	}

}
