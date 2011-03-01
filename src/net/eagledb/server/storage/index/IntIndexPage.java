package net.eagledb.server.storage.index;

import java.util.Enumeration;

// http://www.koders.com/java/fidDD507AA710FB8CFA6B60DD4D21C20DD94DB821E2.aspx?s=btree
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

	public SearchResult searchObj(int key) {
		return searchObj(root, key);
	}

	/*
	 * returns null in the btnode part and -1 in the keyIndex
	 * if the specified key doesn't exist
	 */
	public SearchResult searchObj(BTNode btnode, int key) {
		SearchResult resultObj = new SearchResult(null, -1);
		int i = 0;
		boolean keyNotInNode = false;
		boolean keyFound = false;

		while (!keyNotInNode && !keyFound) {
			if (btnode.getKeyNode(i) != null && key < btnode.getKeyNode(i).getKey()) {
				keyNotInNode = true;
				if (!btnode.isLeaf)
					resultObj = searchObj(btnode.getBTNode(i), key);
			}
			else if (btnode.getKeyNode(i) != null && key == btnode.getKeyNode(i).getKey()) {
				keyFound = true;
				resultObj = new SearchResult(btnode, i); //key found
			}
			else if (i < (btnode.nKey - 1))
				i++;
			else if (btnode.getKeyNode(i) != null && key > btnode.getKeyNode(i).getKey()) {
				keyNotInNode = true;
				if (!btnode.isLeaf)
					resultObj = searchObj(btnode.getBTNode(i + 1), key);
			}
			else
				keyNotInNode = true;
		}
		
		return resultObj;
	}

}
