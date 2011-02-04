package net.eagledb.server.sql.type;

/**
 * "REAL" and "FLOAT"
 */
public class Real extends SQLType {

	static {
		SQLType.registerClass(new Real());
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
		return new String[] { "REAL", "FLOAT" };
	}

}
