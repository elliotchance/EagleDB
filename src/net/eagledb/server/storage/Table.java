package net.eagledb.server.storage;

import net.eagledb.server.storage.page.*;
import java.util.ArrayList;

public class Table {

	public String name;

	public TransactionPage transactionPageHead = null;

	public TransactionPage transactionPageTail = null;
	
	public ArrayList<Page> pageHeads = null;

	public ArrayList<Page> pageTails = null;

	private ArrayList<Field> fields;

	public Table(String tableName) {
		name = tableName;
		fields = new ArrayList<Field>();
		pageHeads = new ArrayList<Page>();
		pageTails = new ArrayList<Page>();
	}

	public boolean addField(Field f) {
		pageHeads.add(null);
		pageTails.add(null);
		fields.add(f);
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

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void addTuple(Tuple t) {
		// do we have space in the last page?
		if(transactionPageTail == null) {
			// allocate room
			transactionPageHead = transactionPageTail = new TransactionPage();

			try {
				int i = 0;
				for(Field f : fields) {
					if(f.pageType.equals(net.eagledb.server.sql.type.Integer.class)) {
						IntPage page = new IntPage();
						pageHeads.set(i, page);
						pageTails.set(i, page);
					}
					else if(f.pageType.equals(net.eagledb.server.sql.type.Real.class)) {
						RealPage page = new RealPage();
						pageHeads.set(i, page);
						pageTails.set(i, page);
					}
					else
						throw new Exception("Unknown attribute type");
					++i;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		// field data
		int i = 0;
		for(Field f : fields) {
			if(f.pageType.equals(net.eagledb.server.sql.type.Integer.class))
				pageTails.get(i).addTuple(Integer.valueOf(t.attributes[i].toString()));
			else if(f.pageType.equals(net.eagledb.server.sql.type.Real.class))
				pageTails.get(i).addTuple(Float.valueOf(t.attributes[i].toString()));
			++i;
		}

		// the transaction page is created after the attributes have been put in
		transactionPageTail.addTuple(1);
	}

	/*public long getTotalTuples() {
		long total = 0;
		for(TransactionPage page : transactionPages)
			total += page.getTotalTuples();
		return total;
	}*/

}
