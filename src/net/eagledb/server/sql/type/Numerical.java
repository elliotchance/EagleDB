package net.eagledb.server.sql.type;

/**
 * "NUMERICAL" and "DECIMAL"
 */
public class Numerical extends SQLType {

	static {
		SQLType.registerClass(new Numerical());
	}

	public boolean isFixedWidth() {
		return false;
	}

	public int getFixedSize() {
		return 4;
	}

	public int getMinimumVariableSize() {
		return 1;
	}

	public int getMaximumVariableSize() {
		return 1000;
	}

	public String[] getNames() {
		return new String[] { "NUMERICAL", "DECIMAL" };
	}

}
