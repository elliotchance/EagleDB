package net.eagledb.server.sql.type;

/**
 * "CHAR" and "CHARACTER"
 */
public class Char extends SQLType {

	static {
		SQLType.registerClass(new Char());
	}

	public boolean isFixedWidth() {
		return false;
	}

	public int getFixedSize() {
		return 4;
	}

	public int getMinimumVariableSize() {
		return 0;
	}

	public int getMaximumVariableSize() {
		return java.lang.Integer.MAX_VALUE;
	}

	public String[] getNames() {
		return new String[] { "CHAR", "CHARACTER" };
	}

}
