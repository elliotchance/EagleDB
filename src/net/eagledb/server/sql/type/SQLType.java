package net.eagledb.server.sql.type;

import java.util.*;

public abstract class SQLType {

	public static Hashtable<String, Class<? extends SQLType>> types = new Hashtable();

	public abstract boolean isFixedWidth();

	public abstract int getFixedSize();

	public abstract int getMinimumVariableSize();

	public abstract int getMaximumVariableSize();

	public abstract String[] getNames();

	public void registerTypes() {
		SQLType.registerClass(this);
	}
	
	public static void registerClass(SQLType type) {
		for(String name : type.getNames())
			types.put(name, type.getClass());
	}

	public static Class<? extends SQLType> getClassForType(String name) {
		return (Class<? extends SQLType>) types.get(name);
	}

	public static void registerAll() {
		new BigInt().registerTypes();
		new Blob().registerTypes();
		new Boolean().registerTypes();
		new Char().registerTypes();
		new Date().registerTypes();
		new DoublePrecision().registerTypes();
		new Integer().registerTypes();
		new Numerical().registerTypes();
		new Real().registerTypes();
		new Time().registerTypes();
		new Timestamp().registerTypes();
		new VarChar().registerTypes();
	}

}
