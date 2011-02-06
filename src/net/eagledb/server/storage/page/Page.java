package net.eagledb.server.storage.page;

import net.eagledb.server.planner.*;
import java.util.*;
import net.eagledb.server.storage.*;

public abstract class Page {

	public static final int TUPLES_PER_PAGE = 1000;

	public int tuples;

	public abstract boolean addTuple(int value);

	public abstract boolean addTuple(float value);

	public abstract void scan(TransactionPage tp, ArrayList<Tuple> tuples, PageAction action, Object value);

}
