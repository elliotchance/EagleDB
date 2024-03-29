package net.eagledb.server.storage;

import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import net.eagledb.server.storage.page.Page;

public class Attribute implements Serializable {

	protected String name;

	protected Class<? extends Page> pageType;

	protected transient RandomAccessFile dataHandle;

	public transient ArrayList<Page> pages = null;

	public Attribute(String fieldName, Class<? extends Page> fieldPageType) {
		name = fieldName;
		pageType = fieldPageType;
		initTransient();
	}

	public Attribute(String fieldName, Class<? extends Page> fieldPageType, RandomAccessFile dataHandle) {
		this(fieldName, fieldPageType);
		this.dataHandle = dataHandle;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pageType
	 */
	public Class<? extends Page> getPageType() {
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

	public final void initTransient() {
		pages = new ArrayList<Page>();
	}

}
