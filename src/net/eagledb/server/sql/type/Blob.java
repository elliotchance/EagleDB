package net.eagledb.server.sql.type;

/**
 * "BLOB"
 */
public class Blob extends SQLType {

	static {
		SQLType.registerClass(new Blob());
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
		return new String[] { "BLOB" };
	}

	public Class getPageClass() {
		return null;
	}

}
