package net.sf.jsqlparser.statement.show.databases;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

/**
 * A "SHOW DATABASES" statement
 */
public class ShowDatabases implements Statement {

    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    @Override
	public String toString() {
        return "SHOW DATABASES";
    }

}
