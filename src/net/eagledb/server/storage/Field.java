package net.eagledb.server.storage;

import java.io.*;
import net.eagledb.server.sql.type.*;

public class Field implements Serializable {

	public String name;

	public Class<? extends SQLType> pageType;

	public Field(String fieldName, Class<? extends SQLType> fieldPageType) {
		name = fieldName;
		pageType = fieldPageType;
	}

}
