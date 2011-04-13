package net.eagledb.server.planner;

import java.lang.reflect.Method;

public class Function {

	public Method method;

	public String name;

	public Class returnType;

	public Class argumentType;

	public Function(String name, Method method, Class returnType, Class argumentType) {
		this.name = name;
		this.method = method;
		this.returnType = returnType;
		this.argumentType = argumentType;
	}

}
