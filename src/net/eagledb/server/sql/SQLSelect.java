package net.eagledb.server.sql;

import java.sql.SQLException;
import java.util.ArrayList;
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
import net.eagledb.server.planner.OrderBy;
import net.eagledb.server.planner.OrderByAttribute;
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
import net.sf.jsqlparser.statement.select.OrderByElement;
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

		// create the executation plan
		Plan p = new Plan();

		// see if the table exists
		PlainSelect select = (PlainSelect) sql.getSelectBody();
		Table table = null;
		if(select.getFromItem() != null) {
			table = schema.getTable(select.getFromItem().toString());
			if(table == null) {
				// temporary table?
				TemporaryTable tt = conn.getTemporaryTable(select.getFromItem().toString());
				if(tt != null)
					table = schema.getTable(tt.internalName);

				if(table == null)
					throw new SQLException("Table " + schema.getName() + "." + select.getFromItem().toString() +
						" does not exist");
			}
		}
		else {
			// using the dual table
			table = Server.dualTable;
			p.pageTuples = 1;
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
			PageOperation[] op = ex.parse(true);

			// see if we discovered an index
			Index bestIndex = ex.getBestIndex();

			// the attributes may also contain ORDER BY
			List<SelectItem> selectItems = select.getSelectItems();
			int totalAttributes = selectItems.size();
			if(select.getOrderByElements() != null)
				totalAttributes += select.getOrderByElements().size();

			// do we need to fetch attributes?
			ArrayList<net.eagledb.server.planner.Expression> faSources =
				new ArrayList<net.eagledb.server.planner.Expression>();
			int[] faDestinations = new int[totalAttributes];
			Attribute[] aliases = new Attribute[totalAttributes];
			int i = 0;
			for(SelectItem theItem : selectItems) {
				SelectExpressionItem item = (SelectExpressionItem) theItem;
				String field = item.getExpression().toString();
				if(field == null)
					field = theItem.toString();

				// alias
				if(item.getAlias() != null)
					aliases[i] = new Attribute(item.getAlias(), null);
				else
					aliases[i] = new Attribute(field, null);

				// try to locate the field
				net.eagledb.server.planner.Expression fex =
					new net.eagledb.server.planner.Expression(table, selectedDatabase, item.getExpression());
				faSources.add(fex);
				
				faDestinations[i] = i;
				++i;
			}

			// find limits
			int limitOffset = 0, limit = Integer.MAX_VALUE;
			if(select.getLimit() != null) {
				limitOffset = (int) select.getLimit().getOffset();
				limit = (int) select.getLimit().getRowCount();
			}

			// if we have an index we can use that
			if(bestIndex != null) {
				IndexLookup lookup = new IndexLookup(table, bestIndex, IndexLookupOperation.EQUAL,
					ex.getBestIndexValue());
				p.addPlanItem(lookup);
				p.addPlanItem(new IndexScan(conn, lookup.virtualTable, totalAttributes, whereClause.toString(), op,
					ex.buffers, limitOffset, limit));
			}
			else {
				FullTableScan fts = new FullTableScan(conn, table, totalAttributes, whereClause.toString(), op,
					ex.buffers, limitOffset, limit);
				p.addPlanItem(fts);
			}

			// fetch attribute projection
			p.addPlanItem(new FetchAttributes(table, faSources, faDestinations));

			// add order by
			if(select.getOrderByElements() != null && select.getOrderByElements().size() > 0) {
				// we don't support nested ordering
				if(select.getOrderByElements().size() > 1)
					throw new SQLException("ORDER BY with multiple attributes is not supported.");

				// compile the ORDER BY expression
				OrderByElement expr = (OrderByElement) select.getOrderByElements().get(0);
				net.eagledb.server.planner.Expression fex = new net.eagledb.server.planner.Expression(table,
					selectedDatabase, expr.getExpression());
				faSources.add(fex);
				faDestinations[selectItems.size()] = selectItems.size();

				ArrayList<OrderByAttribute> exprs = new ArrayList<OrderByAttribute>();
				exprs.add(new OrderByAttribute(faDestinations[selectItems.size()], expr.isAsc(), expr.toString()));
				p.addPlanItem(new OrderBy(table, exprs));
			}

			// execute plan
			if(((net.sf.jsqlparser.statement.select.Select) sql).getExplain()) {
				// if its ANALYZE we need to execute the query
				if(((net.sf.jsqlparser.statement.select.Select) sql).getExplainAnalyse())
					p.execute(conn.transactionID);

				// return the EXPLAIN set
				return new Result(ResultCode.SUCCESS, new Attribute[] {
					new Attribute("explain", net.eagledb.server.storage.page.VarCharPage.class)
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
