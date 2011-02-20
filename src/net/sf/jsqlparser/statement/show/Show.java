package net.sf.jsqlparser.statement.show;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

/**
 * A SHOW DATABASES statement
 */
public class Show implements Statement {

	public ShowObject showObject;

    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

	public ShowObject getShowObject() {
		return showObject;
	}

	public void setShowObject(ShowObject showObject) {
		this.showObject = showObject;
	}

    @Override
	public String toString() {
        return "SHOW " + showObject.toString();
    }

}
