package net.eagledb.server;

import java.io.*;
import net.eagledb.server.storage.*;

public class Result implements Serializable {

	public String sqlException = null;

	public int code = ResultCode.UNKNOWN;

	public Field[] fields;

	public Tuple[] tuples;

	public Result() {
	}

	public Result(int code, Field[] fields, Tuple[] tuples) {
		this.code = code;
		this.fields = fields;
		this.tuples = tuples;
	}

}
