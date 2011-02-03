package net.eagledb.server.storage;

public class Field {

	public String name;

	public Class pageType;

	public Field(String fieldName, Class fieldPageType) {
		name = fieldName;
		pageType = fieldPageType;
	}

}
