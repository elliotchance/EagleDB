package net.eagledb.server.planner;

import net.eagledb.server.storage.page.Page;

public class FunctionException extends ExpressionException {

	private net.sf.jsqlparser.expression.Expression ex;

	private Function function;

	public FunctionException(Function function, net.sf.jsqlparser.expression.Expression ex) {
		super(ex);
		this.ex = ex;
		this.function = function;
	}

	@Override
	public String toString() {
		String argTypes = "";
		try {
			for(int i = 0; i < function.argumentTypes.length; ++i) {
				if(i > 0)
					argTypes += ",";
				argTypes += ((Page) function.argumentTypes[i].newInstance()).getNames()[0];
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "No such function " + function.name + "(" + argTypes + ")";
	}

}
