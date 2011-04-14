package net.eagledb.server.planner;

import java.lang.reflect.Method;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;
import net.eagledb.server.storage.page.VarCharPage;

public class Functions {

	public static Function[] functions;
	
	static {
		functions = new Function[] {
			getFunction("abs", DoublePage.class, DoublePage.class),
			getFunction("abs", DoublePage.class, IntPage.class),
			getFunction("acos", DoublePage.class, DoublePage.class),
			getFunction("acos", DoublePage.class, IntPage.class),
			getFunction("asin", DoublePage.class, DoublePage.class),
			getFunction("asin", DoublePage.class, IntPage.class),
			getFunction("atan", DoublePage.class, DoublePage.class),
			getFunction("atan", DoublePage.class, IntPage.class),
			getFunction("atan2", DoublePage.class, new Class[] { DoublePage.class, DoublePage.class }),
			getFunction("atan2", DoublePage.class, new Class[] { IntPage.class, IntPage.class }),
			getFunction("length", IntPage.class, VarCharPage.class),
			getFunction("cos", DoublePage.class, DoublePage.class),
			getFunction("cos", DoublePage.class, IntPage.class),
			getFunction("sin", DoublePage.class, DoublePage.class),
			getFunction("sin", DoublePage.class, IntPage.class),
			getFunction("tan", DoublePage.class, DoublePage.class),
			getFunction("tan", DoublePage.class, IntPage.class),
		};
	}

	public static Function findFunction(String name, Class[] argumentTypes) {
		for(Function function : functions) {
			if(function.name.equals(name) && java.util.Arrays.equals(function.argumentTypes, argumentTypes))
				return function;
		}
		return null;
	}

	public static Function getFunction(String name, Class returnType, Class argumentType) {
		return getFunction(name, returnType, new Class[] { argumentType });
	}

	public static Function getFunction(String name, Class returnType, Class[] argumentTypes) {
		return new Function(name.toUpperCase(), getFunctionMethod(name, returnType, argumentTypes), returnType, argumentTypes);
	}

	public static Method getFunctionMethod(String name, Class returnType, Class[] argumentTypes) {
		try {
			Class[] args = new Class[1 + argumentTypes.length];
			args[0] = returnType;
			for(int i = 0; i < argumentTypes.length; ++i)
				args[i + 1] = argumentTypes[i];
			return Functions.class.getMethod(name, args);
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

	public static void acos(DoublePage destination, DoublePage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.acos(arg.page[i]);
	}

	public static void acos(DoublePage destination, IntPage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.acos(arg.page[i]);
	}

	public static void asin(DoublePage destination, DoublePage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.asin(arg.page[i]);
	}

	public static void asin(DoublePage destination, IntPage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.asin(arg.page[i]);
	}

	public static void atan(DoublePage destination, DoublePage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.atan(arg.page[i]);
	}

	public static void atan(DoublePage destination, IntPage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.atan(arg.page[i]);
	}

	public static void atan2(DoublePage destination, DoublePage arg1, DoublePage arg2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.atan2(arg1.page[i], arg2.page[i]);
	}

	public static void atan2(DoublePage destination, IntPage arg1, IntPage arg2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = Math.atan2(arg1.page[i], arg2.page[i]);
	}

	public static void length(IntPage destination, VarCharPage arg) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			destination.page[i] = arg.page[i].length();
	}

}
