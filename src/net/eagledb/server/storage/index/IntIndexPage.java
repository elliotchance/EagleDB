package net.eagledb.server.storage.index;

import java.util.ArrayList;
import java.util.Enumeration;
import net.eagledb.server.planner.IndexLookupOperation;
import net.eagledb.server.storage.Tuple;

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

	public ArrayList<Integer> lookup(IndexLookupOperation op, int key) {
		return lookup(root, key);
	}

	/*
	 * returns null in the btnode part and -1 in the keyIndex
	 * if the specified key doesn't exist
	 */
	public ArrayList<Integer> lookup(BTNode btnode, int key) {
		//SearchResult resultObj = new SearchResult(null, -1);
		ArrayList<Integer> resultObj = new ArrayList<Integer>();
		int i = 0;
		boolean keyNotInNode = false;
		boolean keyFound = false;

		while (!keyNotInNode && !keyFound) {
			if (btnode.getKeyNode(i) != null && key < btnode.getKeyNode(i).getKey()) {
				keyNotInNode = true;
				if(!btnode.isLeaf)
					resultObj = lookup(btnode.getBTNode(i), key);
			}
			else if (btnode.getKeyNode(i) != null && key == btnode.getKeyNode(i).getKey()) {
				keyFound = true;
				for(KeyNode kn : btnode.kArray) {
					if(kn == null)
						break;
					if(kn.getKey() == key)
						resultObj.add(kn.getObj());
				}
			}
			else if (i < (btnode.nKey - 1))
				i++;
			else if (btnode.getKeyNode(i) != null && key > btnode.getKeyNode(i).getKey()) {
				keyNotInNode = true;
				if(!btnode.isLeaf)
					resultObj = lookup(btnode.getBTNode(i + 1), key);
			}
			else
				keyNotInNode = true;
		}
		
		return resultObj;
	}

}
