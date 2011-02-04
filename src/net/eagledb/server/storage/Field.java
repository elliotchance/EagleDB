package net.eagledb.server.storage;

import java.io.*;

public class Field implements Serializable {

	public String name;

	public Class pageType;

	public Field(String fieldName, Class fieldPageType) {
		name = fieldName;
		pageType = fieldPageType;
	}

}
