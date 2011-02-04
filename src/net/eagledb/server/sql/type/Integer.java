package net.eagledb.server.sql.type;

/**
 * "INT" and "INTEGER"
 */
public class Integer extends SQLType {

	static {
		SQLType.registerClass(new Integer());
	}

	public boolean isFixedWidth() {
		return true;
	}

	public int getFixedSize() {
		return 4;
	}

	public int getMinimumVariableSize() {
		return 0;
	}

	public int getMaximumVariableSize() {
		return 0;
	}

	public String[] getNames() {
		return new String[] { "INT", "INTEGER" };
	}

}
