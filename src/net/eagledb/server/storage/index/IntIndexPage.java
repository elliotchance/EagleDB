package net.eagledb.server.storage.index;

import java.util.Enumeration;

public class IntIndexPage extends IndexPage {

	//public int[] values = new int[Page.TUPLES_PER_PAGE];
	int order = 10;
	BTNode root = new BTNode(order, null);

	public IntIndexPage() {
	}

	public void insertObj(int key, int data) {
		KeyNode keyNode = new KeyNode(key, data);
		BTNode btNode = root;
		while (!btNode.isLeaf) {
			int i = 0;
			while (keyNode.getKey() > btNode.kArray[i].getKey()) {
				i++;
				if (i == btNode.nKey)
					break;
			}
			btNode = btNode.btnArray[i];
		}
		btNode.insert(keyNode);
		if (root.nKey == order * 2 - 1)
			root.split();
		++tuples;
	}

	public Enumeration values() {
		return new BTreeEnumeration(this, false);
	}

	public Enumeration keys() {
		return new BTreeEnumeration(this, true);
	}
	
	@Override
	public String toString() {
		String r = "IntIndexPage with " + tuples + " values\n";

		Enumeration btenum = keys();
		for(int i = 0; btenum.hasMoreElements(); ++i) {
			if(i > 0)
				r += ",";
			r += btenum.nextElement().toString();
		}

		return r;
	}
}
