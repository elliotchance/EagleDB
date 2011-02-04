package net.eagledb.server.sql.type;

/**
 * "DOUBLE" and "DOUBLE PRECISION"
 */
public class DoublePrecision extends SQLType {

	static {
		SQLType.registerClass(new DoublePrecision());
	}

	public boolean isFixedWidth() {
		return true;
	}

	public int getFixedSize() {
		return 8;
	}

	public int getMinimumVariableSize() {
		return 0;
	}

	public int getMaximumVariableSize() {
		return 0;
	}

	public String[] getNames() {
		return new String[] { "DOUBLE", "DOUBLE PRECISION" };
	}

}
