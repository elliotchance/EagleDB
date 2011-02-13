package net.eagledb.server;

import java.io.IOException;
import net.eagledb.server.storage.Attribute;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Schema;
import net.eagledb.server.storage.Table;
import net.eagledb.server.storage.page.Page;
import net.eagledb.server.storage.page.TransactionPage;

public class BackgroundWriter extends Thread {

	private Server server;

	private static int FLUSH_INTERVAL = 100;

	public BackgroundWriter(Server server) {
		this.server = server;
	}

	private synchronized void flushPages() {
		try {
			for(Database database : server.getDatabases()) {
				for(Schema schema : database.getSchemas()) {
					for(Table table : schema.getTables()) {
						while(table.dirtyTransactionPages.size() > 0) {
							TransactionPage page = table.dirtyTransactionPages.get(0);
							table.transactionPageHandle.getChannel().position(page.getPageSize() * page.pageID);
							page.write(table.transactionPageHandle);
							table.dirtyTransactionPages.remove(0);

							// look for dirty field pages
							for(Attribute attribute : table.getAttributes()) {
								Page p = attribute.pages.get(page.pageID);
								attribute.getDataHandle().getChannel().position(page.getPageSize() * p.pageID);
								p.write(attribute.getDataHandle());
							}
						}
					}
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while(true) {
				flushPages();
				Thread.sleep(FLUSH_INTERVAL);
			}
		}
		catch(InterruptedException e) {
			// write remaining pages
			e.printStackTrace();
		}
	}

}
