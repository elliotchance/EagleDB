package net.eagledb.server;

import net.eagledb.utils.Properties;
import java.util.ArrayList;

public class Authenticator {

	private ArrayList<Properties> users;

	public Authenticator() {
		// setup allowed users
		users = new ArrayList<Properties>();
		users.add(new Properties("user=root,pass=123"));
	}

	public boolean verifyUser(Properties p) {
		for(Properties user : users) {
			if(user.get("user").equals(p.get("user")) && user.get("pass").equals(p.get("pass")))
				return true;
		}

		return false;
	}

}
