package net.eagledb.utils;

import java.util.*;

public class Properties extends HashMap<String, String> {

	public Properties(String properties) {
		String[] elements = properties.split(",");
		for(String element : elements) {
			int pos = element.indexOf("=");
			put(element.substring(0, pos), element.substring(pos + 1));
		}
	}

	@Override
	public String toString() {
		String s = "(";

		Iterator iterator = keySet().iterator();
        for(int i = 0; iterator.hasNext(); ++i) {
			if(i > 0)
				s += ", ";
			String key = iterator.next().toString();
			s += key + " => \"" + get(key) + "\"";
        }

		s += ")";
		return s;
	}

}
