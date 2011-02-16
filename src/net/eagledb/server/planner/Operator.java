package net.eagledb.server.planner;

import java.lang.reflect.Method;
import net.eagledb.server.storage.page.BooleanPage;
import net.eagledb.server.storage.page.IntPage;

public class Operator {

	public Class lhs;

	public PageAction op;

	public Class rhs;

	public Method method;
	
	public static Operator[] operators;

	static {
		try {
			operators = new Operator[] {
				getPageMethod(IntPage.class, "operatorPlus", IntPage.class, PageAction.PLUS, IntPage.class, IntPage.class),
				getPageMethod(IntPage.class, "operatorEqual", IntPage.class, PageAction.EQUAL, IntPage.class, BooleanPage.class)
			};
		}
		catch(NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public Operator(Class lhs, PageAction op, Class rhs, Method method) {
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
		this.method = method;
	}

	public static Operator getPageMethod(Class c, String name, Class lhs, PageAction action, Class rhs, Class dest) throws NoSuchMethodException {
		return new Operator(lhs, action, rhs, c.getMethod(name, dest, lhs, rhs));
	}

	public static Method getMethodForOperator(Class lhs, PageAction op, Class rhs) {
		for(Operator operator : operators) {
			if(operator.lhs == lhs && operator.op == op && operator.rhs == rhs)
				return operator.method;
		}
		return null;
	}

	@Override
	public String toString() {
		String r = "";
		if(lhs != null)
			r += lhs.getSimpleName();
		r += " " + op + " ";
		if(rhs != null)
			r += rhs.getSimpleName();
		return r;
	}

}
