package net.eagledb.server;

import java.io.Serializable;

public class Request implements Serializable {

	public String sql = null;

	public int action = RequestAction.UNKNOWN;

	public Request(String sql) {
		this.sql = sql;
	}

	public Request(String sql, int action) {
		this.sql = sql;
		this.action = action;
	}

}
