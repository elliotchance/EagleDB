package net.eagledb.server.crypt;

import java.security.*;

public class SHA1 extends Crypt {

	public String crypt(String message) {
		if(message == null)
			message = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(message.getBytes());
			return bytesToHex(md.digest());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
