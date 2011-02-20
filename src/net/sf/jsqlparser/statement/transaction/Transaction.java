package net.sf.jsqlparser.statement.transaction;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

/**
 * BEGIN/COMMIT/ROLLBACK TRANSACTION
 */
public class Transaction implements Statement {

	private TransactionCommand command;

    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

	public void setCommand(TransactionCommand command) {
		this.command = command;
	}

	public TransactionCommand getCommand() {
		return command;
	}

    @Override
	public String toString() {
        return command.toString() + " TRANSACTION";
    }

}
