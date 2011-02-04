package net.eagledb.server.sql.type;

/**
 * "VARCHAR", "CHARACTER VARYING" and "TEXT"
 */
public class VarChar extends SQLType {

	static {
		SQLType.registerClass(new VarChar());
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
		return new String[] { "VARCHAR", "CHARACTER VARYING" };
	}

}
