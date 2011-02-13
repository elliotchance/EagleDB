package net.eagledb.server.storage;

import java.io.DataOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import net.eagledb.server.sql.type.SQLType;
import net.eagledb.server.storage.page.Page;

public class Attribute implements Serializable {

	private String name;

	private Class<? extends SQLType> pageType;

	private transient RandomAccessFile dataHandle;

	public transient ArrayList<Page> pages = null;

	public Attribute(String fieldName, Class<? extends SQLType> fieldPageType) {
		name = fieldName;
		pageType = fieldPageType;
		initTransient();
	}

	public Attribute(String fieldName, Class<? extends SQLType> fieldPageType, RandomAccessFile dataHandle) {
		this(fieldName, fieldPageType);
		this.dataHandle = dataHandle;
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

	public void setDataHandle(RandomAccessFile dataHandle) {
		this.dataHandle = dataHandle;
	}

	public RandomAccessFile getDataHandle() {
		return dataHandle;
	}

	public void initTransient() {
		pages = new ArrayList<Page>();
	}

}
