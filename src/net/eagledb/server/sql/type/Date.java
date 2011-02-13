package net.eagledb.server.sql.type;

/**
 * "DATE"
 */
public class Date extends SQLType {

	static {
		SQLType.registerClass(new Date());
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
		return new String[] { "DATE" };
	}

	public Class getPageClass() {
		return null;
	}

}
