package net.eagledb.server.storage.page;

import java.io.DataOutputStream;
import java.io.IOException;

public class RealPage extends Page {

	public float[] page;

	public RealPage() {
		page = new float[Page.TUPLES_PER_PAGE];
	}

	public boolean addTuple(int value) {
		page[tuples++] = value;
		return true;
	}

	public boolean addTuple(float value) {
		page[tuples++] = value;
		return true;
	}

	@Override
	public synchronized void write(DataOutputStream os) throws IOException {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i)
			os.writeFloat(page[i]);
	}

	public int getPageSize() {
		return 4 * Page.TUPLES_PER_PAGE;
	}

}
