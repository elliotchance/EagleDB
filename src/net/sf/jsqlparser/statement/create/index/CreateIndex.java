package net.sf.jsqlparser.statement.create.index;

import java.util.ArrayList;
import net.sf.jsqlparser.schema.Column;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

/**
 * A "CREATE INDEX" statement
 */
public class CreateIndex implements Statement {

    private String table;
	private String index;
	private boolean unique = false;
	private boolean ifNotExists = false;
	private ArrayList<String> columns = new ArrayList<String>();

    public void accept(StatementVisitor statementVisitor) {
        //statementVisitor.visit(this);
    }

    /**
     * The name of the table to be created
     */
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

	@Override
    public String toString() {
        String sql = "CREATE";
		if(isUnique())
			sql += " UNIQUE";
		sql += " INDEX " + getIndex() + " ON " + table + "(";

		int i = 0;
		for(String column : columns) {
			if(i++ > 0)
				sql += ", ";
			sql += column;
		}

        return sql;
    }

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * @param unique the unique to set
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * @return the ifNotExists
	 */
	public boolean isIfNotExists() {
		return ifNotExists;
	}

	/**
	 * @param ifNotExists the ifNotExists to set
	 */
	public void setIfNotExists(boolean ifNotExists) {
		this.ifNotExists = ifNotExists;
	}

	/**
	 * @return the columns
	 */
	public ArrayList<String> getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}
}
