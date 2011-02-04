package net.eagledb.server;

import java.io.Serializable;

public enum RequestAction implements Serializable {

	CLOSE_CONNECTION,
	SQL_QUERY,
	SQL_UPDATE

}
