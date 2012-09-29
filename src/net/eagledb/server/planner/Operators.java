package net.eagledb.server.planner;

import net.eagledb.server.storage.page.BooleanPage;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.Page;
import net.eagledb.server.storage.page.VarCharPage;

public class Operators {

	public static void operatorAdd(IntPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] + page2.page[i]);
		}
	}

	public static void operatorAdd(DoublePage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] + page2.page[i]);
		}
	}

	public static void operatorAdd(DoublePage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] + page2.page[i]);
		}
	}

	public static void operatorAdd(DoublePage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] + page2.page[i]);
		}
	}

	// ---

	public static void operatorSubtract(IntPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] - page2.page[i]);
		}
	}

	public static void operatorSubtract(DoublePage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] - page2.page[i]);
		}
	}

	public static void operatorSubtract(DoublePage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] - page2.page[i]);
		}
	}

	public static void operatorSubtract(DoublePage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] - page2.page[i]);
		}
	}

	// ---

	public static void operatorMultiply(IntPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] * page2.page[i]);
		}
	}

	public static void operatorMultiply(DoublePage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] * page2.page[i]);
		}
	}

	public static void operatorMultiply(DoublePage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] * page2.page[i]);
		}
	}

	public static void operatorMultiply(DoublePage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] * page2.page[i]);
		}
	}

	// ---

	public static void operatorDivide(DoublePage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] / page2.page[i]);
		}
	}

	public static void operatorDivide(DoublePage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] / page2.page[i]);
		}
	}

	public static void operatorDivide(DoublePage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] / page2.page[i]);
		}
	}

	public static void operatorDivide(DoublePage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] / page2.page[i]);
		}
	}

	// ---

	public static void operatorEqual(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] == page2.page[i]);
		}
	}

	public static void operatorEqual(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] == page2.page[i]);
		}
	}

	public static void operatorEqual(BooleanPage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] == page2.page[i]);
		}
	}

	public static void operatorEqual(BooleanPage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] == page2.page[i]);
		}
	}

	// ---

	public static void operatorNotEqual(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] != page2.page[i]);
		}
	}

	public static void operatorNotEqual(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] != page2.page[i]);
		}
	}

	public static void operatorNotEqual(BooleanPage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] != page2.page[i]);
		}
	}

	public static void operatorNotEqual(BooleanPage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] != page2.page[i]);
		}
	}

	// ---

	public static void operatorGreater(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] > page2.page[i]);
		}
	}

	public static void operatorGreater(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] > page2.page[i]);
		}
	}

	public static void operatorGreater(BooleanPage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] > page2.page[i]);
		}
	}

	public static void operatorGreater(BooleanPage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] > page2.page[i]);
		}
	}

	// ---

	public static void operatorLess(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] < page2.page[i]);
		}
	}

	public static void operatorLess(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] < page2.page[i]);
		}
	}

	public static void operatorLess(BooleanPage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] < page2.page[i]);
		}
	}

	public static void operatorLess(BooleanPage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] < page2.page[i]);
		}
	}

	// ---

	public static void operatorGreaterEqual(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] >= page2.page[i]);
		}
	}

	public static void operatorGreaterEqual(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] >= page2.page[i]);
		}
	}

	public static void operatorGreaterEqual(BooleanPage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] >= page2.page[i]);
		}
	}

	public static void operatorGreaterEqual(BooleanPage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] >= page2.page[i]);
		}
	}

	// ---

	public static void operatorLessEqual(BooleanPage destination, IntPage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] <= page2.page[i]);
		}
	}

	public static void operatorLessEqual(BooleanPage destination, IntPage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] <= page2.page[i]);
		}
	}

	public static void operatorLessEqual(BooleanPage destination, DoublePage page1, IntPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] <= page2.page[i]);
		}
	}

	public static void operatorLessEqual(BooleanPage destination, DoublePage page1, DoublePage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page1.page[i] <= page2.page[i]);
		}
	}

	// ---

	public static void operatorCast(BooleanPage destination, IntPage page) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page.page[i] != 0);
		}
	}

	public static void operatorCast(BooleanPage destination, DoublePage page) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = (page.page[i] != 0.0);
		}
	}

	// ---

	public static void operatorConcat(VarCharPage destination, VarCharPage page1, VarCharPage page2) {
		for(int i = 0; i < Page.TUPLES_PER_PAGE; ++i) {
			destination.page[i] = page1.page[i] + page2.page[i];
		}
	}

}
