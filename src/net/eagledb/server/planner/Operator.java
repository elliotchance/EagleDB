package net.eagledb.server.planner;

import java.lang.reflect.Method;
import net.eagledb.server.storage.page.BooleanPage;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.VarCharPage;

public class Operator {

	/**
	 * Left hand side.
	 */
	public Class lhs;

	public PageAction op;

	/**
	 * Right hand side.
	 */
	public Class rhs;

	public Method method;
	
	public static Operator[] operators;

	protected static Class o = Operators.class;

	static {
		try {
			operators = new Operator[] {
				getPageMethod("operatorCast", IntPage.class, PageAction.CAST, BooleanPage.class),
				getPageMethod("operatorCast", DoublePage.class, PageAction.CAST, BooleanPage.class),

				getPageMethod("operatorEqual", IntPage.class, PageAction.EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod("operatorEqual", IntPage.class, PageAction.EQUAL, DoublePage.class, BooleanPage.class),
				getPageMethod("operatorEqual", DoublePage.class, PageAction.EQUAL, DoublePage.class, BooleanPage.class),
				getPageMethod("operatorEqual", DoublePage.class, PageAction.EQUAL, IntPage.class, BooleanPage.class),

				getPageMethod("operatorNotEqual", IntPage.class, PageAction.NOT_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod("operatorNotEqual", IntPage.class, PageAction.NOT_EQUAL, DoublePage.class, BooleanPage.class),
				getPageMethod("operatorNotEqual", DoublePage.class, PageAction.NOT_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod("operatorNotEqual", DoublePage.class, PageAction.NOT_EQUAL, DoublePage.class, BooleanPage.class),

				getPageMethod("operatorGreater", IntPage.class, PageAction.GREATER_THAN, IntPage.class, BooleanPage.class),
				getPageMethod("operatorGreater", IntPage.class, PageAction.GREATER_THAN, DoublePage.class, BooleanPage.class),
				getPageMethod("operatorGreater", DoublePage.class, PageAction.GREATER_THAN, IntPage.class, BooleanPage.class),
				getPageMethod("operatorGreater", DoublePage.class, PageAction.GREATER_THAN, DoublePage.class, BooleanPage.class),

				getPageMethod("operatorGreaterEqual", IntPage.class, PageAction.GREATER_THAN_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod("operatorGreaterEqual", IntPage.class, PageAction.GREATER_THAN_EQUAL, DoublePage.class, BooleanPage.class),
				getPageMethod("operatorGreaterEqual", DoublePage.class, PageAction.GREATER_THAN_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod("operatorGreaterEqual", DoublePage.class, PageAction.GREATER_THAN_EQUAL, DoublePage.class, BooleanPage.class),

				getPageMethod("operatorLess", IntPage.class, PageAction.LESS_THAN, IntPage.class, BooleanPage.class),
				getPageMethod("operatorLess", IntPage.class, PageAction.LESS_THAN, DoublePage.class, BooleanPage.class),
				getPageMethod("operatorLess", DoublePage.class, PageAction.LESS_THAN, IntPage.class, BooleanPage.class),
				getPageMethod("operatorLess", DoublePage.class, PageAction.LESS_THAN, DoublePage.class, BooleanPage.class),

				getPageMethod("operatorLessEqual", IntPage.class, PageAction.LESS_THAN_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod("operatorLessEqual", IntPage.class, PageAction.LESS_THAN_EQUAL, DoublePage.class, BooleanPage.class),
				getPageMethod("operatorLessEqual", DoublePage.class, PageAction.LESS_THAN_EQUAL, IntPage.class, BooleanPage.class),
				getPageMethod("operatorLessEqual", DoublePage.class, PageAction.LESS_THAN_EQUAL, DoublePage.class, BooleanPage.class),

				getPageMethod("operatorAdd", IntPage.class, PageAction.ADD, IntPage.class, IntPage.class),
				getPageMethod("operatorAdd", DoublePage.class, PageAction.ADD, DoublePage.class, DoublePage.class),
				getPageMethod("operatorAdd", DoublePage.class, PageAction.ADD, IntPage.class, DoublePage.class),
				getPageMethod("operatorAdd", IntPage.class, PageAction.ADD, DoublePage.class, DoublePage.class),

				getPageMethod("operatorSubtract", IntPage.class, PageAction.SUBTRACT, IntPage.class, IntPage.class),
				getPageMethod("operatorSubtract", IntPage.class, PageAction.SUBTRACT, DoublePage.class, DoublePage.class),
				getPageMethod("operatorSubtract", DoublePage.class, PageAction.SUBTRACT, IntPage.class, DoublePage.class),
				getPageMethod("operatorSubtract", DoublePage.class, PageAction.SUBTRACT, DoublePage.class, DoublePage.class),

				getPageMethod("operatorMultiply", IntPage.class, PageAction.MULTIPLY, IntPage.class, IntPage.class),
				getPageMethod("operatorMultiply", IntPage.class, PageAction.MULTIPLY, DoublePage.class, DoublePage.class),
				getPageMethod("operatorMultiply", DoublePage.class, PageAction.MULTIPLY, IntPage.class, DoublePage.class),
				getPageMethod("operatorMultiply", DoublePage.class, PageAction.MULTIPLY, DoublePage.class, DoublePage.class),

				getPageMethod("operatorDivide", IntPage.class, PageAction.DIVIDE, IntPage.class, DoublePage.class),
				getPageMethod("operatorDivide", IntPage.class, PageAction.DIVIDE, DoublePage.class, DoublePage.class),
				getPageMethod("operatorDivide", DoublePage.class, PageAction.DIVIDE, IntPage.class, DoublePage.class),
				getPageMethod("operatorDivide", DoublePage.class, PageAction.DIVIDE, DoublePage.class, DoublePage.class),

				getPageMethod("operatorConcat", VarCharPage.class, PageAction.CONCAT, VarCharPage.class, VarCharPage.class),
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

	public static Operator getPageMethod(String name, Class lhs, PageAction action, Class dest)
		throws NoSuchMethodException {
		return new Operator(lhs, action, null, o.getMethod(name, dest, lhs));
	}

	public static Operator getPageMethod(String name, Class lhs, PageAction action, Class rhs, Class dest)
		throws NoSuchMethodException {
		return new Operator(lhs, action, rhs, o.getMethod(name, dest, lhs, rhs));
	}

	public static Method getMethodForOperator(Class lhs, PageAction op, Class rhs) {
		for(Operator operator : operators) {
			if(operator.lhs == lhs && operator.op == op && operator.rhs == rhs) {
				return operator.method;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String r = "";
		if(lhs != null) {
			r += lhs.getSimpleName();
		}
		r += " " + op + " ";
		if(rhs != null) {
			r += rhs.getSimpleName();
		}
		return r;
	}

}
