package net.eagledb.server.planner;

import java.lang.reflect.Method;

public class Function {

	public Method method;

	public String name;

	public Class returnType;

	public Class[] argumentTypes;

	public Function(String name, Method method, Class returnType, Class[] argumentTypes) {
		this.name = name;
		this.method = method;
		this.returnType = returnType;
		this.argumentTypes = argumentTypes;
	}

}
