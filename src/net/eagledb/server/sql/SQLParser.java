package net.eagledb.server.sql;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import java.io.StringReader;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
//import net.sf.jsqlparser.statement.create.table.CreateTable;

public class SQLParser {

	private CCJSqlParserManager parserManager = new CCJSqlParserManager();

	public SQLParser() {
	}

	public String parse(String sql) {
		String result;
		try {
			Statement stmt = parserManager.parse(new StringReader(sql));
			//if(stmt instanceof CreateTable)
			//	new SQLCreateTable((CreateTable) stmt);
			//System.out.println(createTable.getColumnDefinitions());
			result = "Success";
		}
		catch(JSQLParserException e) {
			result = e.getCause().toString();
		}
		return result;
	}

}
