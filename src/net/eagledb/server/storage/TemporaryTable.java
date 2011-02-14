package net.eagledb.server.storage;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TemporaryTable {

	public String name;

	public String internalName;

	public TemporaryTable(String name, String internalName) {
		this.name = name;
		this.internalName = internalName;
	}

	public TemporaryTable(String name) {
		this(name, new BigInteger(130, new SecureRandom()).toString(32));
	}

	@Override
	public String toString() {
		return name + " => " + internalName;
	}

}
