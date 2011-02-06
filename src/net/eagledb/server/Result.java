package net.eagledb.server;

import java.io.*;
import net.eagledb.server.storage.*;
import java.util.*;

public class Result implements Serializable {

	public String sqlException = null;

	public int code = ResultCode.UNKNOWN;

	public Attribute[] fields;

	public Tuple[] tuples;

	public Result(int code) {
		this.code = code;
	}

	public Result(int code, Attribute[] fields, Tuple[] tuples) {
		this.code = code;
		this.fields = fields;
		this.tuples = tuples;
	}

	public Result(int code, ArrayList<Attribute> fields, Tuple[] tuples) {
		this.code = code;
		this.fields = new Attribute[fields.size()];
		int i = 0;
		for(Attribute f : fields)
			this.fields[i++] = f;
		this.tuples = tuples;
	}

}
