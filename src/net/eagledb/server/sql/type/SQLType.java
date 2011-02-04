package net.eagledb.server.sql.type;

import java.util.*;

public abstract class SQLType {

	public static Hashtable types = new Hashtable();

	public abstract boolean isFixedWidth();

	public abstract int getFixedSize();

	public abstract int getMinimumVariableSize();

	public abstract int getMaximumVariableSize();

	public abstract String[] getNames();
	
	public static void registerClass(SQLType type) {
		for(String name : type.getNames())
			types.put(name, type);
	}

	public static Class getClassForType(String name) {
		return (Class) types.get(name);
	}

}
