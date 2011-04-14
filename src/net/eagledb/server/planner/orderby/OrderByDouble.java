package net.eagledb.server.planner.orderby;

public class OrderByDouble extends Orderable {

	public double[] data;

	protected int size = 0;

	public OrderByDouble(int size) {
		tupleIDs = new int[size];
		data = new double[size];
	}

	public int size() {
		return size;
	}

	public void push(int tupleID, double tupleValue) {
		tupleIDs[size] = tupleID;
		data[size] = tupleValue;
		++size;
	}

	public void sortAscending() {
		// simple bubble sort, i'll put in a better sort later
		int n = size;
		do {
			int newn = 0;
			for(int i = 0; i < n - 1; ++i) {
				if(data[i] > data[i + 1]) {
					int tempTupleID = tupleIDs[i];
					double tempData = data[i];
					tupleIDs[i] = tupleIDs[i + 1];
					data[i] = data[i + 1];
					tupleIDs[i + 1] = tempTupleID;
					data[i + 1] = tempData;
					newn = i + 1;
				}
			}
			n = newn;
		} while(n > 1);
	}

	public void sortDescending() {
		// simple bubble sort, i'll put in a better sort later
		int n = size;
		do {
			int newn = 0;
			for(int i = 0; i < n - 1; ++i) {
				if(data[i] < data[i + 1]) {
					int tempTupleID = tupleIDs[i];
					double tempData = data[i];
					tupleIDs[i] = tupleIDs[i + 1];
					data[i] = data[i + 1];
					tupleIDs[i + 1] = tempTupleID;
					data[i + 1] = tempData;
					newn = i + 1;
				}
			}
			n = newn;
		} while(n > 1);
	}

}
