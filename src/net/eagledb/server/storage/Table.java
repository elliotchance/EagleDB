package net.eagledb.server.storage;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import net.eagledb.server.planner.PlanItemCost;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;
import net.eagledb.server.storage.page.RealPage;
import net.eagledb.server.storage.page.TransactionPage;

public class Table implements java.io.Serializable {

	protected String name;

	protected transient ArrayList<TransactionPage> transactionPages = null;
	
	public transient ArrayList<TransactionPage> dirtyTransactionPages = null;

	public transient RandomAccessFile transactionPageHandle = null;

	protected ArrayList<Attribute> attributes;

	protected int totalPages = 0;

	protected Table() {
		// do nothing, this is for duplication
	}

	public Table(String tableName, Attribute[] attrs) {
		name = tableName;
		attributes = new ArrayList<Attribute>();
		initTransient();

		if(attrs != null) {
			for(Attribute attr : attrs)
				addAttribute(attr);
		}
	}

	public void createVirtualTable(Table r, int pageID) {
		r.name = name;
		r.transactionPages = new ArrayList<TransactionPage>();
		r.dirtyTransactionPages = new ArrayList<TransactionPage>();
		r.attributes = attributes;
		r.totalPages = 1;

		int i = 0;
		r.transactionPages.add(transactionPages.get(pageID));
		for(Attribute attr : r.attributes) {
			attr.pages.add(attributes.get(i++).pages.get(pageID));
		}
	}

	public synchronized void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public final void initTransient() {
		transactionPages = new ArrayList<TransactionPage>();
		dirtyTransactionPages = new ArrayList<TransactionPage>();
		for(Attribute attribute : attributes)
			attribute.initTransient();
	}

	public final boolean addAttribute(Attribute f) {
		attributes.add(f);
		return true;
	}

	public Attribute[] getAttributes() {
		Attribute[] r = new Attribute[attributes.size()];
		int i = 0;
		for(Attribute a : attributes)
			r[i++] = a;
		return r;
	}

	public void addTuple(Tuple t, long transactionID) {
		// do we have space in the last page?
		if(transactionPages.isEmpty() ||
			transactionPages.get(transactionPages.size() - 1).getTotalTuples() >= Page.TUPLES_PER_PAGE) {
			TransactionPage tp = new TransactionPage();
			tp.pageID = transactionPages.size();
			transactionPages.add(tp);
			++totalPages;

			try {
				int i = 0;
				for(Attribute f : attributes) {
					Class<? extends net.eagledb.server.sql.type.SQLType> pageType = f.getPageType();
					Page p = null;

					if(pageType.equals(net.eagledb.server.sql.type.Integer.class))
						p = new IntPage();
					else if(pageType.equals(net.eagledb.server.sql.type.Real.class))
						p = new RealPage();
					else if(pageType.equals(net.eagledb.server.sql.type.DoublePrecision.class))
						p = new DoublePage();
					else
						throw new Exception("Unknown attribute type " + pageType);

					p.pageID = transactionPages.size() - 1;
					attributes.get(i).pages.add(p);
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
			else if(pageType.equals(net.eagledb.server.sql.type.DoublePrecision.class))
				tail.addTuple(Double.valueOf(t.get(i).toString()));
			++i;
		}

		// the transaction page is created after the attributes have been put in
		transactionPages.get(transactionPages.size() - 1).addTransactionTuple(transactionID);

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

	public TransactionPage getTransactionPage(int location, PlanItemCost cost) {
		// do we have the page already in RAM?
		if(location < transactionPages.size()) {
			++cost.pagesReadFromCache;
			return transactionPages.get(location);
		}

		// we have to fetch it, don't cache it
		++cost.pagesReadFromDisk;
		try {
			transactionPageHandle.getChannel().position(location * TransactionPage.getPageSize());
			
			TransactionPage tp = new TransactionPage();
			tp.pageID = location;
			tp.read(transactionPageHandle);

			// can we cache this page?
			if(location == transactionPages.size())
				transactionPages.add(tp);
			
			return tp;
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Page getPage(int fieldID, int location, PlanItemCost cost) {
		// do we have the page already in RAM?
		Attribute attribute = attributes.get(fieldID);
		if(location < attribute.pages.size()) {
			++cost.pagesReadFromCache;
			return attribute.pages.get(location);
		}

		// we have to fetch it, don't cache it
		++cost.pagesReadFromDisk;
		try {
			Page page = (Page) attribute.getPageType().newInstance().getPageClass().newInstance();
			attribute.getDataHandle().getChannel().position(location * page.getPageSize());

			page.pageID = location;
			page.read(attribute.getDataHandle());

			// can we cache this page?
			if(location == attribute.pages.size())
				attribute.pages.add(page);

			return page;
		}
		catch(InstantiationException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void addDirtyTransactionPage(TransactionPage p) {
		if(dirtyTransactionPages.contains(p))
			return;
		dirtyTransactionPages.add(p);
	}

}
