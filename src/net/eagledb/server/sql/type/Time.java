package net.eagledb.server.sql.type;

/**
 * "TIME"
 */
public class Time extends SQLType {

	static {
		SQLType.registerClass(new Time());
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
		return new String[] { "TIME" };
	}

	public Class getPageClass() {
		return null;
	}

}
