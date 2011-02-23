package net.eagledb.wiki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Parser {

	protected String simpleTags(String line) {
		// bold & italic
		while(line.indexOf("'''''") >= 0)
			line = line.replaceFirst("'''''", "<b><i>").replaceFirst("'''''", "</i></b>");

		// bold
		while(line.indexOf("'''") >= 0)
			line = line.replaceFirst("'''", "<b>").replaceFirst("'''", "</b>");

		// italic
		while(line.indexOf("''") >= 0)
			line = line.replaceFirst("''", "<i>").replaceFirst("''", "</i>");

		return line;
	}

	protected String parseLine(String line) {
		if(line.equals(""))
			return "<br/><br/>";

		// headings
		if(line.startsWith("======"))
			return "<h6>" + line.substring(6, line.lastIndexOf("=") - 5) + "</h6>";
		if(line.startsWith("====="))
			return "<h5>" + line.substring(5, line.lastIndexOf("=") - 4) + "</h5>";
		if(line.startsWith("===="))
			return "<h4>" + line.substring(4, line.lastIndexOf("=") - 3) + "</h4>";
		if(line.startsWith("==="))
			return "<h3>" + line.substring(3, line.lastIndexOf("=") - 2) + "</h3>";
		if(line.startsWith("=="))
			return "<h2>" + line.substring(2, line.lastIndexOf("=") - 1) + "</h2>";
		if(line.startsWith("="))
			return "<h1>" + line.substring(1, line.lastIndexOf("=")) + "</h1>";

		return simpleTags(line);
	}

	public String parse(String name) {
		try {
			InputStream resource = Parser.class.getResourceAsStream("/net/eagledb/doc/" + name + ".wiki");
			BufferedReader in = new BufferedReader(new InputStreamReader(resource, Charset.defaultCharset()));

			StringBuilder result = new StringBuilder("");
			String line = "";
			while((line = in.readLine()) != null)
				result.append(parseLine(line));
			result.append("");

			return result.toString();
		}
		catch(IOException e) {
			return e.getMessage();
		}
	}
	
}
