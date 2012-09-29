package net.eagledb.server.planner;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.eagledb.server.storage.page.DoublePage;
import net.eagledb.server.storage.page.IntPage;
import net.eagledb.server.storage.page.VarCharPage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Functions {

	public static Function[] functions;

	protected static DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	
	protected static DocumentBuilder docBuilder = null;
	
	static {
		functions = new Function[] {
			getFunction("abs", DoublePage.class, DoublePage.class),
			getFunction("abs", DoublePage.class, IntPage.class),
			getFunction("acos", DoublePage.class, DoublePage.class),
			getFunction("acos", DoublePage.class, IntPage.class),
			getFunction("asin", DoublePage.class, DoublePage.class),
			getFunction("asin", DoublePage.class, IntPage.class),
			getFunction("atan", DoublePage.class, DoublePage.class),
			getFunction("atan", DoublePage.class, IntPage.class),
			getFunction("atan2", DoublePage.class, new Class[] { DoublePage.class, DoublePage.class }),
			getFunction("atan2", DoublePage.class, new Class[] { IntPage.class, IntPage.class }),
			getFunction("length", IntPage.class, VarCharPage.class),
			getFunction("cbrt", DoublePage.class, DoublePage.class),
			getFunction("cbrt", DoublePage.class, IntPage.class),
			getFunction("ceil", DoublePage.class, DoublePage.class),
			getFunction("ceil", DoublePage.class, IntPage.class),
			getFunction("ceiling", DoublePage.class, DoublePage.class),
			getFunction("ceiling", DoublePage.class, IntPage.class),
			getFunction("cos", DoublePage.class, DoublePage.class),
			getFunction("cos", DoublePage.class, IntPage.class),
			getFunction("degrees", DoublePage.class, DoublePage.class),
			getFunction("div", IntPage.class, new Class[] { DoublePage.class, DoublePage.class }),
			getFunction("div", IntPage.class, new Class[] { IntPage.class, IntPage.class }),
			getFunction("exp", DoublePage.class, DoublePage.class),
			getFunction("exp", DoublePage.class, IntPage.class),
			getFunction("floor", DoublePage.class, DoublePage.class),
			getFunction("floor", DoublePage.class, IntPage.class),
			getFunction("ln", DoublePage.class, DoublePage.class),
			getFunction("ln", DoublePage.class, IntPage.class),
			getFunction("log", DoublePage.class, DoublePage.class),
			getFunction("log", DoublePage.class, IntPage.class),
			getFunction("lower", VarCharPage.class, VarCharPage.class),
			getFunction("position", IntPage.class, new Class[] { VarCharPage.class, VarCharPage.class }),
			getFunction("radians", DoublePage.class, DoublePage.class),
			getFunction("sin", DoublePage.class, DoublePage.class),
			getFunction("sin", DoublePage.class, IntPage.class),
			getFunction("substring_from", VarCharPage.class, new Class[] { VarCharPage.class, IntPage.class }),
			getFunction("substring_for", VarCharPage.class, new Class[] { VarCharPage.class, IntPage.class }),
			getFunction("substring_from_for", VarCharPage.class, new Class[] { VarCharPage.class, IntPage.class, IntPage.class }),
			getFunction("tan", DoublePage.class, DoublePage.class),
			getFunction("tan", DoublePage.class, IntPage.class),
			getFunction("trim", VarCharPage.class, new Class[] { VarCharPage.class, VarCharPage.class }),
			getFunction("trim_leading", VarCharPage.class, new Class[] { VarCharPage.class, VarCharPage.class }),
			getFunction("trim_trailing", VarCharPage.class, new Class[] { VarCharPage.class, VarCharPage.class }),
			getFunction("upper", VarCharPage.class, VarCharPage.class),
			getFunction("xmlcomment", VarCharPage.class, VarCharPage.class),
			getVarArgsFunction("xmlconcat", VarCharPage.class),
			getFunction("xmlroot", VarCharPage.class, VarCharPage.class),
			getFunction("xmlroot", VarCharPage.class, new Class[] { VarCharPage.class, VarCharPage.class }),
			getFunction("xmlroot", VarCharPage.class, new Class[] { VarCharPage.class, VarCharPage.class, VarCharPage.class }),
		};
	}

	public static Function findFunction(String name, Class[] argumentTypes) {
		for(Function function : functions) {
			if(function.name.equals(name) && java.util.Arrays.equals(function.argumentTypes, argumentTypes)) {
				return function;
			}
		}
		return null;
	}

	public static Function findVarArgsFunction(String name) {
		for(Function function : functions) {
			if(function.name.equals(name) &&
				java.util.Arrays.equals(function.argumentTypes, new Class[] { Object[].class })) {
				return function;
			}
		}
		return null;
	}

	public static Function getVarArgsFunction(String name, Class returnType) {
		try {
		return new Function(name.toUpperCase(), Functions.class.getMethod(name, new Class[] { Object[].class }),
			returnType, new Class[] { Object[].class } );
		}
		catch(NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Function getFunction(String name, Class returnType, Class argumentType) {
		return getFunction(name, returnType, new Class[] { argumentType });
	}

	public static Function getFunction(String name, Class returnType, Class[] argumentTypes) {
		return new Function(name.toUpperCase(), getFunctionMethod(name, returnType, argumentTypes), returnType,
			argumentTypes);
	}

	public static Method getFunctionMethod(String name, Class returnType, Class[] argumentTypes) {
		try {
			Class[] args = new Class[2 + argumentTypes.length];
			args[0] = int.class;
			args[1] = returnType;
			for(int i = 0; i < argumentTypes.length; ++i) {
				args[i + 2] = argumentTypes[i];
			}
			return Functions.class.getMethod(name, args);
		}
		catch(NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void abs(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.abs(arg.page[i]);
		}
	}

	public static void abs(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.abs(arg.page[i]);
		}
	}

	public static void cos(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.cos(arg.page[i]);
		}
	}

	public static void cos(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.cos(arg.page[i]);
		}
	}

	public static void sin(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.sin(arg.page[i]);
		}
	}

	public static void sin(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.sin(arg.page[i]);
		}
	}

	public static void tan(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.tan(arg.page[i]);
		}
	}

	public static void tan(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.tan(arg.page[i]);
		}
	}

	public static void acos(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.acos(arg.page[i]);
		}
	}

	public static void acos(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.acos(arg.page[i]);
		}
	}

	public static void asin(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.asin(arg.page[i]);
		}
	}

	public static void asin(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.asin(arg.page[i]);
		}
	}

	public static void atan(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.atan(arg.page[i]);
		}
	}

	public static void atan(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.atan(arg.page[i]);
		}
	}

	public static void atan2(int tuples, DoublePage destination, DoublePage arg1, DoublePage arg2) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.atan2(arg1.page[i], arg2.page[i]);
		}
	}

	public static void atan2(int tuples, DoublePage destination, IntPage arg1, IntPage arg2) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.atan2(arg1.page[i], arg2.page[i]);
		}
	}

	public static void length(int tuples, IntPage destination, VarCharPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = arg.page[i].length();
		}
	}

	public static void cbrt(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.cbrt(arg.page[i]);
		}
	}

	public static void cbrt(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.cbrt(arg.page[i]);
		}
	}

	public static void ceil(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.ceil(arg.page[i]);
		}
	}

	public static void ceil(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.ceil(arg.page[i]);
		}
	}

	public static void ceiling(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.ceil(arg.page[i]);
		}
	}

	public static void ceiling(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.ceil(arg.page[i]);
		}
	}

	public static void radians(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.toRadians(arg.page[i]);
		}
	}

	public static void degrees(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.toDegrees(arg.page[i]);
		}
	}

	public static void div(int tuples, IntPage destination, DoublePage arg1, DoublePage arg2) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = (int) (arg1.page[i] / arg2.page[i]);
		}
	}

	public static void div(int tuples, IntPage destination, IntPage arg1, IntPage arg2) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = (int) ((double) arg1.page[i] / (double) arg2.page[i]);
		}
	}

	public static void ln(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.log(arg.page[i]);
		}
	}

	public static void ln(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.log(arg.page[i]);
		}
	}

	public static void log(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.log10(arg.page[i]);
		}
	}

	public static void log(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.log10(arg.page[i]);
		}
	}

	public static void exp(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.exp(arg.page[i]);
		}
	}

	public static void exp(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.exp(arg.page[i]);
		}
	}

	public static void floor(int tuples, DoublePage destination, DoublePage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.floor(arg.page[i]);
		}
	}

	public static void floor(int tuples, DoublePage destination, IntPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = Math.floor(arg.page[i]);
		}
	}

	public static void xmlroot(int tuples, VarCharPage destination, VarCharPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = "<?xml version=\"1.0\"?>\n" + arg.page[i];
		}
	}

	public static void xmlroot(int tuples, VarCharPage destination, VarCharPage arg, VarCharPage version) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = "<?xml version=\"" + version.page[i] + "\"?>\n" + arg.page[i];
		}
	}

	public static void xmlroot(int tuples, VarCharPage destination, VarCharPage arg, VarCharPage version, VarCharPage standalone) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = "<?xml version=\"" + version.page[i] + "\" standalone=\"" + standalone.page[i] +
				"\"?>\n" + arg.page[i];
		}
	}

	public static void xmlcomment(int tuples, VarCharPage destination, VarCharPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = "<!--" + arg.page[i] + "-->";
		}
	}

	public static void xmlconcat(Object[] args) {
		int tuples = Integer.valueOf(args[0].toString());
		VarCharPage dest = (VarCharPage) args[1];

		for(int i = 0; i < tuples; ++i) {
			try {
				// prepare
				if(docBuilder == null) {
					docBuilder = dbfac.newDocumentBuilder();
				}
				Document doc = docBuilder.newDocument();

				// new virtual root
				Element root = doc.createElement("root");

				// build
				String xmlDec = "";
				for(int j = 2; j < args.length; ++j) {
					// we use the XML declaration from the first argument (if one exists)
					VarCharPage p = (VarCharPage) args[j];
					if(xmlDec.equals("") && p.page[i].startsWith("<?xml")) {
						xmlDec = p.page[i].substring(0, p.page[i].indexOf("?>") + 2);
					}
					
					Document merge = docBuilder.parse(new InputSource(new StringReader(p.page[i])));
					root.appendChild(doc.importNode(merge.getDocumentElement(), true));
				}

				// set up a transformer
				TransformerFactory transfac = TransformerFactory.newInstance();
				Transformer trans = transfac.newTransformer();
				trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				//trans.setOutputProperty(OutputKeys.INDENT, "yes");

				// create string from xml tree
				StringWriter sw = new StringWriter();
				StreamResult result = new StreamResult(sw);
				NodeList childNodes = root.getChildNodes();
				for(int j = 0; j < childNodes.getLength(); ++j) {
					DOMSource source = new DOMSource(childNodes.item(j));
					trans.transform(source, result);
				}

				dest.page[i] = xmlDec + sw.toString();
			}
			catch(ParserConfigurationException ex) {
				ex.printStackTrace();
				dest.page[i] = ex.getMessage();
			}
			catch (TransformerConfigurationException ex) {
				ex.printStackTrace();
				dest.page[i] = ex.getMessage();
			}
			catch (TransformerException ex) {
				ex.printStackTrace();
				dest.page[i] = ex.getMessage();
			}
			catch (SAXException ex) {
				ex.printStackTrace();
				dest.page[i] = ex.getMessage();
			}
			catch (IOException ex) {
				ex.printStackTrace();
				dest.page[i] = ex.getMessage();
			}
		}
	}

	public static void position(int tuples, IntPage destination, VarCharPage substring, VarCharPage string) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = string.page[i].indexOf(substring.page[i]) + 1;
		}
	}

	public static void substring_from(int tuples, VarCharPage destination, VarCharPage string, IntPage from) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = string.page[i].substring(from.page[i] - 1);
		}
	}

	public static void substring_for(int tuples, VarCharPage destination, VarCharPage string, IntPage to) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = string.page[i].substring(0, to.page[i]);
		}
	}

	public static void substring_from_for(int tuples, VarCharPage destination, VarCharPage string, IntPage from,
		IntPage to) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = string.page[i].substring(from.page[i] - 1, to.page[i] + 1);
		}
	}

	public static void upper(int tuples, VarCharPage destination, VarCharPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = arg.page[i].toUpperCase();
		}
	}

	public static void lower(int tuples, VarCharPage destination, VarCharPage arg) {
		for(int i = 0; i < tuples; ++i) {
			destination.page[i] = arg.page[i].toLowerCase();
		}
	}

	public static void trim(int tuples, VarCharPage destination, VarCharPage string, VarCharPage characters) {
		for(int i = 0; i < tuples; ++i) {
			if(characters.page[i].length() == 0) {
				destination.page[i] = string.page[i].trim();
				continue;
			}

			destination.page[i] = string.page[i];

			// front
			while(destination.page[i].startsWith(characters.page[i])) {
				destination.page[i] = destination.page[i].substring(characters.page[i].length());
			}

			// end
			while(destination.page[i].endsWith(characters.page[i])) {
				destination.page[i] = destination.page[i].substring(0,
					destination.page[i].length() - characters.page[i].length());
			}
		}
	}

	public static void trim_leading(int tuples, VarCharPage destination, VarCharPage string, VarCharPage characters) {
		for(int i = 0; i < tuples; ++i) {
			if(characters.page[i].length() == 0) {
				destination.page[i] = string.page[i].replaceAll("^\\s+", "");
				continue;
			}

			destination.page[i] = string.page[i];
			while(destination.page[i].startsWith(characters.page[i])) {
				destination.page[i] = destination.page[i].substring(characters.page[i].length());
			}
		}
	}

	public static void trim_trailing(int tuples, VarCharPage destination, VarCharPage string, VarCharPage characters) {
		for(int i = 0; i < tuples; ++i) {
			if(characters.page[i].length() == 0) {
				destination.page[i] = string.page[i].replaceAll("\\s+$", "");
				continue;
			}

			destination.page[i] = string.page[i];
			while(destination.page[i].endsWith(characters.page[i])) {
				destination.page[i] = destination.page[i].substring(0,
					destination.page[i].length() - characters.page[i].length());
			}
		}
	}

}
