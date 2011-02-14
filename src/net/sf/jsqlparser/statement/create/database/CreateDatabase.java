package net.sf.jsqlparser.statement.create.database;

import net.sf.jsqlparser.statement.create.database.*;
import java.util.List;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.schema.Column;

/**
 * A "CREATE DATABASE" statement
 */
public class CreateDatabase implements Statement {

    private String name;

    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
	public String toString() {
        return "CREATE DATABASE " + name;
    }

}