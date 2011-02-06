package net.eagledb.server.storage;

import net.eagledb.server.storage.page.*;
import java.util.ArrayList;

public class Table implements java.io.Serializable {

	private String name;

	public transient TransactionPage transactionPageHead = null;

	public transient TransactionPage transactionPageTail = null;
	
	public transient ArrayList<Page> pageHeads = null;

	public transient ArrayList<Page> pageTails = null;

	private ArrayList<Attribute> attributes;

	public Table(String tableName, Attribute[] attrs) {
		name = tableName;
		attributes = new ArrayList<Attribute>();
		pageHeads = new ArrayList<Page>();
		pageTails = new ArrayList<Page>();

		for(Attribute attr : attrs)
			addAttribute(attr);
	}

	public boolean addAttribute(Attribute f) {
		pageHeads.add(null);
		pageTails.add(null);
		attributes.add(f);
		return true;
	}

	public boolean addPage() {
		// add transaction page
		//transactionPageHead.add(new TransactionPage());

		// add field pages
		/*try {
			int i = 0;
			for(Field f : fields) {
				pages.get(i).add((Page) fields.get(i).pageType.newInstance());
				++i;
			}
		}
		catch(InstantiationException e) {
			e.printStackTrace();
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}*/

		return true;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public void addTuple(Tuple t) {
		// do we have space in the last page?
		if(transactionPageTail == null) {
			// allocate room
			transactionPageHead = transactionPageTail = new TransactionPage();

			try {
				int i = 0;
				for(Attribute f : attributes) {
					Class<? extends net.eagledb.server.sql.type.SQLType> pageType = f.getPageType();

					if(pageType.equals(net.eagledb.server.sql.type.Integer.class)) {
						IntPage page = new IntPage();
						pageHeads.set(i, page);
						pageTails.set(i, page);
					}
					else if(pageType.equals(net.eagledb.server.sql.type.Real.class)) {
						RealPage page = new RealPage();
						pageHeads.set(i, page);
						pageTails.set(i, page);
					}
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

			if(pageType.equals(net.eagledb.server.sql.type.Integer.class))
				pageTails.get(i).addTuple(Integer.valueOf(t.get(i).toString()));
			else if(pageType.equals(net.eagledb.server.sql.type.Real.class))
				pageTails.get(i).addTuple(Float.valueOf(t.get(i).toString()));
			++i;
		}

		// the transaction page is created after the attributes have been put in
		transactionPageTail.addTuple(1);
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

}
