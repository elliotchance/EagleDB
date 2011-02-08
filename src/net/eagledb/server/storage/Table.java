package net.eagledb.server.storage;

import java.io.DataOutputStream;
import java.util.ArrayList;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;
import net.eagledb.server.storage.page.RealPage;
import net.eagledb.server.storage.page.TransactionPage;

public class Table implements java.io.Serializable {

	private String name;

	private transient ArrayList<TransactionPage> transactionPages = null;
	
	public transient ArrayList<TransactionPage> dirtyTransactionPages = null;

	public transient DataOutputStream transactionPageHandle = null;

	private ArrayList<Attribute> attributes;

	public Table(String tableName, Attribute[] attrs) {
		name = tableName;
		attributes = new ArrayList<Attribute>();
		initTransient();

		for(Attribute attr : attrs)
			addAttribute(attr);
	}

	public void initTransient() {
		transactionPages = new ArrayList<TransactionPage>();
		dirtyTransactionPages = new ArrayList<TransactionPage>();
		for(Attribute attribute : attributes)
			attribute.initTransient();
	}

	public boolean addAttribute(Attribute f) {
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
						attributes.get(i).pages.add(new IntPage());
					else if(pageType.equals(net.eagledb.server.sql.type.Real.class))
						attributes.get(i).pages.add(new RealPage());
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
		Page tail = null;
		for(Attribute f : attributes) {
			Class<? extends net.eagledb.server.sql.type.SQLType> pageType = f.getPageType();

			tail = attributes.get(i).pages.get(attributes.get(i).pages.size() - 1);
			if(pageType.equals(net.eagledb.server.sql.type.Integer.class))
				tail.addTuple((int) ((double) Double.valueOf(t.get(i).toString())));
			else if(pageType.equals(net.eagledb.server.sql.type.Real.class))
				tail.addTuple((float) ((double) Double.valueOf(t.get(i).toString())));
			++i;
		}

		// the transaction page is created after the attributes have been put in
		transactionPages.get(transactionPages.size() - 1).addTransactionTuple(1);

		// add the dirty pages
		addDirtyTransactionPage(transactionPages.get(transactionPages.size() - 1));
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
		return attributes.get(fieldID).pages.get(location);
	}

	public int getTotalPages() {
		return transactionPages.size();
	}

	private void addDirtyTransactionPage(TransactionPage p) {
		if(dirtyTransactionPages.contains(p))
			return;
		dirtyTransactionPages.add(p);
	}

}
