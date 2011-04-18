package net.eagledb.server.planner;

public enum PageAction {

	ALL,

	EQUAL {
		@Override
		public String toString() {
			return "=";
		}
	},
	NOT_EQUAL {
		@Override
		public String toString() {
			return "<>";
		}
	},

	GREATER_THAN {
		@Override
		public String toString() {
			return ">";
		}
	},
	LESS_THAN {
		@Override
		public String toString() {
			return "<";
		}
	},
	GREATER_THAN_EQUAL {
		@Override
		public String toString() {
			return ">=";
		}
	},
	LESS_THAN_EQUAL {
		@Override
		public String toString() {
			return "<=";
		}
	},

	ADD {
		@Override
		public String toString() {
			return "+";
		}
	},

	SUBTRACT {
		@Override
		public String toString() {
			return "-";
		}
	},

	MULTIPLY {
		@Override
		public String toString() {
			return "*";
		}
	},

	DIVIDE {
		@Override
		public String toString() {
			return "/";
		}
	},

	AND,
	OR,

	CAST {
		@Override
		public String toString() {
			return "::";
		}
	},

	CONCAT {
		@Override
		public String toString() {
			return "||";
		}
	}

}
