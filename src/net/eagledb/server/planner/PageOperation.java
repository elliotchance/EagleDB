package net.eagledb.server.planner;

import net.eagledb.server.storage.page.*;

public abstract class PageOperation {

	public int fieldID;

	public PageAction action;

	public abstract int getMaxBuffer();

	public abstract void run(TransactionPage tp, Page p, boolean[][] buffer);

	@Override
	public abstract String toString();

}
