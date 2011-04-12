package net.eagledb.server.sql;

import java.sql.SQLException;
import java.util.List;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.Result;
import net.eagledb.server.ResultCode;
import net.eagledb.server.Server;
import net.eagledb.server.planner.ExpressionException;
import net.eagledb.server.planner.FetchAttributes;
import net.eagledb.server.planner.FullTableScan;
import net.eagledb.server.planner.IndexLookup;
import net.eagledb.server.planner.IndexLookupOperation;
import net.eagledb.server.planner.IndexScan;
import net.eagledb.server.planner.PageOperation;
import net.eagledb.server.planner.Plan;
import net.eagledb.server.storage.Attribute;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Index;
import net.eagledb.server.storage.Schema;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.TemporaryTable;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class SQLSelect extends SQLAction {
	
	private Select sql;

	public SQLSelect(Server server, ClientConnection conn, Select sql) {
		super(server, conn);
		this.sql = sql;
	}

	public Result getResult() throws SQLException {
		// we must have a selected database
		Database selectedDatabase = conn.getSelectedDatabase();
		if(selectedDatabase == null)
			throw new SQLException("No database selected.");

		// check the users permission
		//if(!conn.getUser().canCreateTable)
		//	throw new SQLException("Permission denied. You must have the CREATE TABLE privilege.");

		// get schema
		Schema schema = selectedDatabase.getSchema("public");
		if(schema == null)
			throw new SQLException("No such schema " + schema.getName());

		// see if the table exists
		PlainSelect select = (PlainSelect) sql.getSelectBody();
		Table table = schema.getTable(select.getFromItem().toString());
		if(table == null) {
			// temporary table?
			TemporaryTable tt = conn.getTemporaryTable(select.getFromItem().toString());
			if(tt != null)
				table = schema.getTable(tt.internalName);

			if(table == null)
				throw new SQLException("Table " + schema.getName() + "." + select.getFromItem().toString() +
					" does not exist");
		}

		// parse expression operations
		try {
			// extract WHERE clause, making sure it is not empty
			Expression whereClause = select.getWhere();
			if(whereClause == null)
				whereClause = new LongValue("1");

			// parse the expression
			net.eagledb.server.planner.Expression ex = new net.eagledb.server.planner.Expression(table,
				selectedDatabase, whereClause);
			PageOperation[] op = ex.parse();

			// see if we discovered an index
			Index bestIndex = ex.getBestIndex();

			// do we need to fetch attributes?
			List<SelectItem> selectItems = select.getSelectItems();
			int[] faSources = new int[selectItems.size()];
			int[] faDestinations = new int[selectItems.size()];
			Class[] faTypes = new Class[selectItems.size()];
			Attribute[] aliases = new Attribute[table.getAttributes().length]; //table.getAttributes().clone();
			int i = 0;
			for(SelectItem theItem : selectItems) {
				SelectExpressionItem item = (SelectExpressionItem) theItem;
				String field = item.getExpression().toString();
				if(field == null)
					field = theItem.toString();

				// alias
				if(item.getAlias() != null)
					aliases[i] = new Attribute(item.getAlias(), table.getAttributes()[i].getPageType());
				else
					aliases[i] = new Attribute(field, table.getAttributes()[i].getPageType());

				// try to locate the field
				int position = table.getAttributeLocation(field);
				if(position < 0)
					throw new Exception("Column '" + field + "' not found");

				faSources[i] = position;
				faDestinations[i] = i;
				faTypes[i] = table.getAttributes()[position].getPageType();
				++i;
			}

			// create the executation plan
			Plan p = new Plan();

			// find limits
			int limitOffset = 0, limit = Integer.MAX_VALUE;
			if(select.getLimit() != null) {
				limitOffset = (int) select.getLimit().getOffset();
				limit = (int) select.getLimit().getRowCount();
			}

			// if we have an index we can use that
			if(bestIndex != null) {
				IndexLookup lookup = new IndexLookup(table, bestIndex, IndexLookupOperation.EQUAL, ex.getBestIndexValue());
				p.addPlanItem(lookup);
				p.addPlanItem(new IndexScan(conn.getSelectedDatabase(), lookup.virtualTable, selectItems.size(),
					whereClause.toString(), op, ex.buffers, limitOffset, limit));
			}
			else {
				p.addPlanItem(new FullTableScan(conn.getSelectedDatabase(), table, selectItems.size(),
					whereClause.toString(), op, ex.buffers, limitOffset, limit));
			}

			// fetch attribute projection
			p.addPlanItem(new FetchAttributes(table, faSources, faDestinations, faTypes));

			// execute plan
			if(((net.sf.jsqlparser.statement.select.Select) sql).getExplain()) {
				// if its ANALYZE we need to execute the query
				if(((net.sf.jsqlparser.statement.select.Select) sql).getExplainAnalyse())
					p.execute(conn.transactionID);

				// return the EXPLAIN set
				return new Result(ResultCode.SUCCESS, new Attribute[] {
					new Attribute("explain", net.eagledb.server.sql.type.VarChar.class)
				}, p.getExplainTuples());
			}
			else
				p.execute(conn.transactionID);

			return new Result(ResultCode.SUCCESS, aliases, p.getTuples());
		}
		catch(ExpressionException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return new Result(ResultCode.FAILED);
	}

}
