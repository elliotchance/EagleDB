package net.eagledb.server;

import java.io.IOException;
import net.eagledb.server.storage.Attribute;
import net.eagledb.server.storage.Database;
import net.eagledb.server.storage.Schema;
import net.eagledb.server.storage.Table;
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
							page.write(table.transactionPageHandle);
							table.dirtyTransactionPages.remove(0);
							System.out.println("DIRTY TRANSACTION PAGE WRITTEN");
							
							/*for(Attribute attribute : table.getAttributes()) {

							}*/
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
