package net.sf.jsqlparser.statement.block;

import java.util.ArrayList;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.transaction.TransactionCommand;

/**
 * BEGIN ... END
 */
public class Block implements Statement {

	private ArrayList<Statement> statements = new ArrayList<Statement>();

    public void accept(StatementVisitor statementVisitor) {
        //statementVisitor.visit(this);
    }

	public void addStatement(Statement statement) {
		statements.add(statement);
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

    @Override
	public String toString() {
		String r = "BEGIN\n";
		for(Statement s : statements)
			r += s.toString() + "\n";
        return r + "END";
    }

}
