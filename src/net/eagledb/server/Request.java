package net.eagledb.server;

import java.io.Serializable;

public class Request implements Serializable {

	public String sql = null;

	public RequestAction requestAction = null;

	public boolean isUpdate = false;

	public Request(String sql) {
		this.sql = sql;
		requestAction = RequestAction.SQL_QUERY;
	}

}
