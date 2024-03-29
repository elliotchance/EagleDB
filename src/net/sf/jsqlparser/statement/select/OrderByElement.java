/* ================================================================
 * JSQLParser : java based sql parser 
 * ================================================================
 *
 * Project Info:  http://jsqlparser.sourceforge.net
 * Project Lead:  Leonardo Francalanci (leoonardoo@yahoo.it);
 *
 * (C) Copyright 2004, by Leonardo Francalanci
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */
package net.sf.jsqlparser.statement.select;

import net.sf.jsqlparser.expression.Expression;

/**
 * An element (column reference) in an "ORDER BY" clause.
 */
public class OrderByElement {

	private Expression expr;
	private boolean asc = true;

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean b) {
		asc = b;
	}

	public void accept(OrderByVisitor orderByVisitor) {
		orderByVisitor.visit(this);
	}

	public Expression getExpression() {
		return expr;
	}

	public void setExpression(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "" + expr + ((asc) ? "" : " DESC");
	}

}
