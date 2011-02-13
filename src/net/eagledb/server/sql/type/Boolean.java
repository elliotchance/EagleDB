package net.eagledb.server.sql.type;

/**
 * "BOOL" and "BOOLEAN"
 */
public class Boolean extends SQLType {

	static {
		SQLType.registerClass(new Boolean());
	}

	public boolean isFixedWidth() {
		return false;
	}

	public int getFixedSize() {
		return 1;
	}

	public int getMinimumVariableSize() {
		return 0;
	}

	public int getMaximumVariableSize() {
		return java.lang.Integer.MAX_VALUE;
	}

	public String[] getNames() {
		return new String[] { "BOOL", "BOOLEAN" };
	}

	public Class getPageClass() {
		return null;
	}

}
