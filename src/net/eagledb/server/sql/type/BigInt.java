package net.eagledb.server.sql.type;

/**
 * "BIGINT"
 */
public class BigInt extends SQLType {

	static {
		SQLType.registerClass(new BigInt());
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
		return new String[] { "BIGINT" };
	}

}
