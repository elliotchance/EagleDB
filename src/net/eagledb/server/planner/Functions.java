package net.eagledb.server.planner;

import java.lang.reflect.Method;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;

public class Functions {

	public static Function[] functions;
	
	static {
		functions = new Function[] {
			getFunction("abs", DoublePage.class, DoublePage.class),
			getFunction("abs", DoublePage.class, IntPage.class),
			getFunction("cos", DoublePage.class, DoublePage.class),
			getFunction("cos", DoublePage.class, IntPage.class),
			getFunction("sin", DoublePage.class, DoublePage.class),
			getFunction("sin", DoublePage.class, IntPage.class),
			getFunction("tan", DoublePage.class, DoublePage.class),
			getFunction("tan", DoublePage.class, IntPage.class),
		};
	}

	public static Function findFunction(String name, Class argumentType) {
		for(Function function : functions) {
			if(function.name.equals(name) && function.argumentType == argumentType)
				return function;
		}
		return null;
	}

	public static Function getFunction(String name, Class returnType, Class argumentType) {
		return new Function(name.toUpperCase(), getFunctionMethod(name, returnType, argumentType), returnType, argumentType);
	}

	public static Method getFunctionMethod(String name, Class returnType, Class argumentType) {
		try {
			return Functions.class.getMethod(name, new Class[] { returnType, argumentType });
		}
		catch(NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void abs(DoublePage destination, DoublePage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.abs(arg.page[i]);
	}

	public static void abs(DoublePage destination, IntPage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.abs(arg.page[i]);
	}

	public static void cos(DoublePage destination, DoublePage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.cos(arg.page[i]);
	}

	public static void cos(DoublePage destination, IntPage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.cos(arg.page[i]);
	}

	public static void sin(DoublePage destination, DoublePage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.sin(arg.page[i]);
	}

	public static void sin(DoublePage destination, IntPage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.sin(arg.page[i]);
	}

	public static void tan(DoublePage destination, DoublePage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.tan(arg.page[i]);
	}

	public static void tan(DoublePage destination, IntPage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.tan(arg.page[i]);
	}

}
