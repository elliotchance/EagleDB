package net.eagledb.server.storage;

import net.eagledb.server.storage.page.TransactionPage;
import net.eagledb.server.storage.page.Page;
import net.eagledb.server.storage.page.IntPage;
import java.util.ArrayList;

public class Table {

	public String name;

	public ArrayList<TransactionPage> transactionPages;
	public ArrayList<ArrayList<Page>> pages;

	private ArrayList<Field> fields;

	public Table(String tableName) {
		name = tableName;
		transactionPages = new ArrayList<TransactionPage>();
		fields = new ArrayList<Field>();
		pages = new ArrayList<ArrayList<Page>>();
	}

	public boolean addField(Field f) {
		pages.add(new ArrayList<Page>());
		fields.add(f);
		return true;
	}

	public boolean addPage() {
		// add transaction page
		transactionPages.add(new TransactionPage());

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

	public TransactionPage getTransactionPage(int position) {
		return transactionPages.get(position);
	}

	public Page getPage(int field, int position) {
		return pages.get(field).get(position);
	}

	public void addTuple(Tuple t) {
		// calculate next tuple position
		int destinationPage = 0;
		while(transactionPages.get(destinationPage).getTotalTuples() == 1000)
			++destinationPage;

		try {
			int i = 0;
			for(Field f : fields) {
				if(f.pageType.equals(IntPage.class))
					pages.get(i).get(destinationPage).addTuple(Integer.valueOf(t.attributes[i].toString()));
				else
					throw new Exception("Unknown attribute type");
				++i;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		// the transaction page is created after the attributes have been put in
		transactionPages.get(destinationPage).addTuple(1);
	}

	public long getTotalTuples() {
		long total = 0;
		for(TransactionPage page : transactionPages)
			total += page.getTotalTuples();
		return total;
	}

	public int getTotalPages() {
		return transactionPages.size();
	}

}
