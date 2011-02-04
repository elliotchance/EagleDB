package net.eagledb.server.sql.type;

/**
 * "TIMESTAMP"
 */
public class Timestamp extends SQLType {

	static {
		SQLType.registerClass(new Timestamp());
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
		return new String[] { "TIMESTAMP" };
	}

}
