package net.eagledb.server.planner;

import java.lang.reflect.Method;
import net.eagledb.server.storage.page.BooleanPage;
import net.eagledb.server.storage.page.DoublePage;
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
				getPageMethod(IntPage.class, "operatorCast", IntPage.class, PageAction.CAST, BooleanPage.class),
				getPageMethod(DoublePage.class, "operatorCast", DoublePage.class, PageAction.CAST, BooleanPage.class),

				getPageMethod(IntPage.class, "operatorEqual", IntPage.class, PageAction.EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod(IntPage.class, "operatorEqual", IntPage.class, PageAction.EQUAL, DoublePage.class, BooleanPage.class),
				getPageMethod(DoublePage.class, "operatorEqual", DoublePage.class, PageAction.EQUAL, DoublePage.class, BooleanPage.class),
				getPageMethod(DoublePage.class, "operatorEqual", DoublePage.class, PageAction.EQUAL, IntPage.class, BooleanPage.class),

				getPageMethod(IntPage.class, "operatorNotEqual", IntPage.class, PageAction.NOT_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod(IntPage.class, "operatorNotEqual", IntPage.class, PageAction.NOT_EQUAL, DoublePage.class, BooleanPage.class),

				getPageMethod(IntPage.class, "operatorGreater", IntPage.class, PageAction.GREATER_THAN, IntPage.class, BooleanPage.class),
				getPageMethod(IntPage.class, "operatorGreater", IntPage.class, PageAction.GREATER_THAN, DoublePage.class, BooleanPage.class),

				getPageMethod(IntPage.class, "operatorGreaterEqual", IntPage.class, PageAction.GREATER_THAN_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod(IntPage.class, "operatorGreaterEqual", IntPage.class, PageAction.GREATER_THAN_EQUAL, DoublePage.class, BooleanPage.class),

				getPageMethod(IntPage.class, "operatorLess", IntPage.class, PageAction.LESS_THAN, IntPage.class, BooleanPage.class),
				getPageMethod(IntPage.class, "operatorLess", IntPage.class, PageAction.LESS_THAN, DoublePage.class, BooleanPage.class),

				getPageMethod(IntPage.class, "operatorLessEqual", IntPage.class, PageAction.LESS_THAN_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod(IntPage.class, "operatorLessEqual", IntPage.class, PageAction.LESS_THAN_EQUAL, DoublePage.class, BooleanPage.class),

				getPageMethod(IntPage.class, "operatorAdd", IntPage.class, PageAction.ADD, IntPage.class, IntPage.class),
				getPageMethod(IntPage.class, "operatorAdd", IntPage.class, PageAction.ADD, DoublePage.class, DoublePage.class),
				getPageMethod(IntPage.class, "operatorAdd", DoublePage.class, PageAction.ADD, DoublePage.class, DoublePage.class),

				getPageMethod(IntPage.class, "operatorSubtract", IntPage.class, PageAction.SUBTRACT, IntPage.class, IntPage.class),
				getPageMethod(IntPage.class, "operatorSubtract", IntPage.class, PageAction.SUBTRACT, DoublePage.class, DoublePage.class),

				getPageMethod(IntPage.class, "operatorMultiply", IntPage.class, PageAction.MULTIPLY, IntPage.class, IntPage.class),
				getPageMethod(IntPage.class, "operatorMultiply", IntPage.class, PageAction.MULTIPLY, DoublePage.class, DoublePage.class),

				getPageMethod(IntPage.class, "operatorDivide", IntPage.class, PageAction.DIVIDE, IntPage.class, IntPage.class),
				getPageMethod(IntPage.class, "operatorDivide", IntPage.class, PageAction.DIVIDE, DoublePage.class, DoublePage.class),
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

	public static Operator getPageMethod(Class c, String name, Class lhs, PageAction action, Class dest) throws NoSuchMethodException {
		return new Operator(lhs, action, null, c.getMethod(name, dest, lhs));
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
