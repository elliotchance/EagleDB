package net.eagledb.server.sql;

import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;
import net.eagledb.server.ClientConnection;
import net.eagledb.server.Request;
import net.eagledb.server.Result;
import net.eagledb.server.ResultCode;
import net.eagledb.server.Server;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Index;
import net.eagledb.server.storage.Schema;
import net.eagledb.server.storage.TemporaryTable;
import net.eagledb.server.storage.Tuple;
import net.eagledb.server.storage.index.IntIndexPage;
import net.sf.jsqlparser.statement.create.index.CreateIndex;

public class SQLCreateIndex extends SQLAction {
	
	private CreateIndex sql;

	public SQLCreateIndex(Server server, ClientConnection conn, CreateIndex sql) {
		super(server, conn);
		this.sql = sql;
	}

	private int position = 0;

	// depth starts at 1 and works down to 0
	private int depth = 1;

	private void buildIndex(int[] data, IntIndexPage page, int start, int end) {


		/*byte lastValue = -1, size = 16;
		int lower, higher, child = -1;
		for(int i = start; i < end; ++i) {
			byte b1 = (byte) Math.floor(data[i] / Math.pow(size, depth));
			//System.out.println("divide = " + Math.pow(size, depth) + ", modulus = " + Math.pow(size, 1 - depth));

			lower = -1;
			higher = (i < data.length - 1) ? position + 1 : -1;
			child = -1;

			// handle children first
			if(b1 == lastValue) {
				for(int k = 0; k < depth; ++k)
					System.out.print("  ");
				System.out.print("children between " + i);
				int j;
				for(j = i; j < end; ++j) {
					byte b2 = (byte) Math.floor(data[j] / Math.pow(size, depth));
					if(b2 != lastValue)
						break;
				}
				System.out.println(" and " + (j - 1));

				--depth;
				//System.out.println("divide = " + Math.pow(size, depth) + ", modulus = " + Math.pow(size, 1 - depth));
				buildIndex(data, page, i, j);

				for(int k = i; k < j; ++k) {
					byte b2 = (byte) Math.floor(data[k] / Math.pow(size, depth));
					System.out.println("  " + data[k] + " => " + b2);
				}

				++depth;

				// skip children
				i = j - 1;
				position = i;
				higher = 1000;
				// child = ...
			}

			// parent
			for(int k = 0; k < depth; ++k)
				System.out.print("  ");
			System.out.println("(value, lower, higher, child) = (" + data[i] + ", " + lower + ", " + higher + ", " + child + ")");
			page.addOrdered(data[i], lower, higher, child);
			++position;

			lastValue = b1;
		}*/
	}

	private String prepareIndex(int[] data, int start, int end) {
		String s = "";
		byte lastValue = -1, size = 16;

		for(int i = start; i < end; ++i) {
			byte b1 = (byte) Math.floor(data[i] / Math.pow(size, depth));
			if(b1 != lastValue) {
				for(int k = 0; k < depth; ++k)
					s += " ";
				s += data[i] + "\n";
			}
			lastValue = b1;
		}
		
		return s;
	}

	public Result getResult() throws SQLException {
		// we must have a selected database
		Database selectedDatabase = conn.getSelectedDatabase();
		if(selectedDatabase == null)
			throw new SQLException("No database selected.");

		// check the users permission
		//if(!conn.getUser().canCreateDatabase)
		//	throw new SQLException("Permission denied. You must have the CREATE DATABASE privilege.");

		// create the index
		Index index = new Index();
		index.name = sql.getIndex();
		index.table = sql.getTable();
		index.columns = sql.getColumns();

		// handle TEMPORARY table
		Schema schema = selectedDatabase.getSchema("public");
		if(schema.getTable(sql.getTable()) == null) {
			TemporaryTable tt = conn.getTemporaryTable(sql.getTable());
			if(tt != null)
				index.table = schema.getTable(tt.internalName).getName();
		}

		// generate a SQL statement
		String sqlStmt = "select " + sql.getColumns().get(0) + " from " + sql.getTable();

		// build the index
		IntIndexPage page = new IntIndexPage();
		try {
			Result result = conn.pingPong(new Request(sqlStmt));
			for(Tuple tuple : result.tuples)
				page.insertObj(tuple.tupleID, Integer.valueOf(tuple.get(0).toString()));
			index.page = page;
		}
		catch(EOFException e) {
			// do nothing
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		System.out.println(page);

		// add the index
		selectedDatabase.addIndex(index);

		return new Result(ResultCode.SUCCESS);
	}

}
