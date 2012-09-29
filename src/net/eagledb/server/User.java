package net.eagledb.server;

import java.io.Serializable;
import java.util.ArrayList;
import net.eagledb.server.crypt.*;

public class User implements Serializable {

	/**
	 * User name, cannot be changed once set.
	 */
	private String username;

	/**
	 * The hashed password - using sha1.
	 */
	private String hashedPassword;

	/**
	 * Super user privilage.
	 */
	public boolean isSuperUser = false;

	/**
	 * User has permission to run CREATE DATABASE.
	 */
	public boolean canCreateDatabase = false;

	/**
	 * User has permission to run SHOW DATABASES.
	 */
	public boolean canShowDatabases = false;

	/**
	 * The user has the permission to CREATE SCHEMA in these databases.
	 */
	public ArrayList<String> canCreatSchemaInDatabase = new ArrayList<String>();

	public boolean canCreateTable = false;

	public User(String username, String password) {
		this.username = username;
		this.hashedPassword = new SHA1().crypt(password);
	}

	public String getUsername() {
		return username;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

}
