package net.eagledb.server;

import java.io.*;
import net.eagledb.server.storage.*;

public class Result implements Serializable {

	public String error = null;

	public Field[] fields;

	public Tuple[] tuples;

	public Result() {
	}

	public Result(Field[] fields, Tuple[] tuples) {
		this.fields = fields;
		this.tuples = tuples;
	}

}
