package net.eagledb.server.storage;

import net.eagledb.server.storage.page.*;
import java.util.ArrayList;

public class Table implements java.io.Serializable {

	private String name;

	private transient ArrayList<TransactionPage> transactionPages = null;
	
	private transient ArrayList<ArrayList<Page>> pages = null;

	private ArrayList<Attribute> attributes;

	public Table(String tableName, Attribute[] attrs) {
		name = tableName;
		attributes = new ArrayList<Attribute>();
		transactionPages = new ArrayList<TransactionPage>();
		pages = new ArrayList<ArrayList<Page>>();

		for(Attribute attr : attrs)
			addAttribute(attr);
	}

	public boolean addAttribute(Attribute f) {
		pages.add(new ArrayList<Page>());
		attributes.add(f);
		return true;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public void addTuple(Tuple t) {
		// do we have space in the last page?
		if(transactionPages.size() == 0 ||
			transactionPages.get(transactionPages.size() - 1).getTotalTuples() >= Page.TUPLES_PER_PAGE) {
			transactionPages.add(new TransactionPage());

			try {
				int i = 0;
				for(Attribute f : attributes) {
					Class<? extends net.eagledb.server.sql.type.SQLType> pageType = f.getPageType();

					if(pageType.equals(net.eagledb.server.sql.type.Integer.class))
						pages.get(i).add(new IntPage());
					else if(pageType.equals(net.eagledb.server.sql.type.Real.class))
						pages.get(i).add(new RealPage());
					else
						throw new Exception("Unknown attribute type " + pageType);
					++i;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		// field data
		int i = 0;
		for(Attribute f : attributes) {
			Class<? extends net.eagledb.server.sql.type.SQLType> pageType = f.getPageType();

			Page tail = pages.get(i).get(pages.get(i).size() - 1);
			if(pageType.equals(net.eagledb.server.sql.type.Integer.class))
				tail.addTuple((int) ((double) Double.valueOf(t.get(i).toString())));
			else if(pageType.equals(net.eagledb.server.sql.type.Real.class))
				tail.addTuple((float) ((double) Double.valueOf(t.get(i).toString())));
			++i;
		}

		// the transaction page is created after the attributes have been put in
		transactionPages.get(transactionPages.size() - 1).addTuple(1);
	}

	/**
	 * Return the tables definition as a complete CREATE TABLE statement.
	 * @return SQL statement.
	 */
	@Override
	public String toString() {
		String sql = "CREATE TABLE \"" + name + "\" (";
		int i = 0;
		for(Attribute field : attributes) {
			if(i > 0)
				sql += ", ";
			sql += field.toString();
			++i;
		}
		sql += ")";
		return sql;
	}

	public boolean attributeExists(String column) {
		for(Attribute f : attributes) {
			if(f.getName().equals(column))
				return true;
		}

		return false;
	}

	public String getName() {
		return name;
	}

	public int getAttributeLocation(String attr) {
		int i = 0;
		for(Attribute f : attributes) {
			if(f.getName().equals(attr))
				return i;
			++i;
		}
		return -1;
	}

	public TransactionPage getTransactionPage(int location) {
		return transactionPages.get(location);
	}

	public Page getPage(int fieldID, int location) {
		return pages.get(fieldID).get(location);
	}

	public int getTotalPages() {
		return transactionPages.size();
	}

}
