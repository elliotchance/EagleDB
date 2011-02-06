package net.eagledb.server.storage;

import java.io.*;
import net.eagledb.server.sql.type.*;
import java.lang.reflect.*;

public class Attribute implements Serializable {

	private String name;

	private Class<? extends SQLType> pageType;

	public Attribute(String fieldName, Class<? extends SQLType> fieldPageType) {
		name = fieldName;
		pageType = fieldPageType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the pageType
	 */
	public Class<? extends SQLType> getPageType() {
		return pageType;
	}

	@Override
	public String toString() {
		try {
			Method method = pageType.getMethod("getNames", new Class[] {});
			String[] names = (String[]) method.invoke(pageType.newInstance(), new Object[] {});
			return "\"" + name + "\" " + names[0];
		}
		catch(NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch(InstantiationException e) {
			e.printStackTrace();
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		catch(InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
