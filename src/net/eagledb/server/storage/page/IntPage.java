package net.eagledb.server.storage.page;

import java.io.DataOutputStream;
import java.io.IOException;

public class IntPage extends Page {

	public int[] page;

	public IntPage() {
		page = new int[Page.TUPLES_PER_PAGE];
	}

	public boolean addTuple(int value) {
		page[tuples++] = value;
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = (int) value;
		return true;
	}

	@Override
	public synchronized void write(DataOutputStream os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeInt(page[i]);
	}

	public int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
	}

}
