package cat.xmlReader;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler implements LexicalHandler {
	static private Writer out;
	StringBuffer textBuffer;
	private String indentString = "    "; // Amount to indent
	private int indentLevel = 0;

	public static void main(String[] argv) {
		// Use an instance of ourselves as the SAX event handler
		DefaultHandler handler = new MyHandler();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);

		try {
			// Set up output stream
			out = new OutputStreamWriter(System.out, "UTF8");

			SAXParser saxParser = factory.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setProperty(
					"http://xml.org/sax/properties/lexical-handler", handler);
			saxParser.parse(new File("/Users/czhtbq/workspace/czh/cat-test/src/main/java/cat/xmlReader/myxmlfile.xml"), handler);

		} catch (SAXParseException spe) {
			// Error generated by the parser
			System.out.println("\n** Parsing error" + ", line "
					+ spe.getLineNumber() + ", uri " + spe.getSystemId());
			System.out.println("   " + spe.getMessage());

			// Use the contained exception, if any
			Exception x = spe;
			if (spe.getException() != null) {
				x = spe.getException();
			}
			x.printStackTrace();
		} catch (SAXException sxe) {
			// Error generated by this application
			// (or a parser-initialization error)
			Exception x = sxe;
			if (sxe.getException() != null) {
				x = sxe.getException();
			}
			x.printStackTrace();
		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// ===========================================================
	// SAX DocumentHandler methods
	// ===========================================================
	public void startDocument() throws SAXException {
		nl();
		nl();
		emit("START DOCUMENT");
		nl();
		emit("<?xml version='1.0' encoding='UTF-8'?>");
	}

	public void endDocument() throws SAXException {
		nl();
		emit("END DOCUMENT");

		try {
			nl();
			out.flush();
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	public void startElement(String namespaceURI, String sName, // simple name
			String qName, Attributes attrs) throws SAXException {
		echoText();
		indentLevel++;
		nl();
		emit("ELEMENT: ");

		String eName = sName; // element name

		if ("".equals(eName)) {
			eName = qName; // not namespaceAware
		}

		emit("<" + eName);

		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				String aName = attrs.getLocalName(i); // Attr name

				if ("".equals(aName)) {
					aName = attrs.getQName(i);
				}

				nl();
				emit("   ATTR: ");
				emit(aName);
				emit("\t\"");
				emit(attrs.getValue(i));
				emit("\"");
			}
		}

		if (attrs.getLength() > 0) {
			nl();
		}

		emit(">");
	}

	public void endElement(String namespaceURI, String sName, String qName)
			throws SAXException {
		echoText();
		nl();
		emit("END_ELM: ");

		String eName = sName; // element name

		if ("".equals(eName)) {
			eName = qName; // not namespaceAware
		}

		emit("</" + eName + ">");
		indentLevel--;
	}

	public void characters(char[] buf, int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);

		if (textBuffer == null) {
			textBuffer = new StringBuffer(s);
		} else {
			textBuffer.append(s);
		}
	}

	public void setDocumentLocator(Locator locator) {
		try {
			out.write("LOCATOR:");
			out.write("STS ID:" + locator.getSystemId());
			out.flush();
		} catch (IOException e) {
			// ignore errors
		}
	}

	public void ignorableWhitespace(char buf[], int offset, int Len)
			throws SAXException {
		nl();
		emit("IGNORABLE");
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		nl();
		emit("PROCESS: ");
		emit("<?" + target + " " + data + "?>");
	}

	public void error(SAXParseException e) throws SAXParseException {
		System.out.println("The document is not valid!!!" + e.getMessage());
		throw e;
	}

	public void warning(SAXParseException err) throws SAXParseException {
		System.out.println("** Warning" + ", line " + err.getLineNumber()
				+ ", uri " + err.getSystemId());
		System.out.println("   " + err.getMessage());
	}

	public void comment(char[] ch, int start, int length) throws SAXException {
		String text = new String(ch, start, length);
		nl();
		emit("COMMENT: " + text);
	}

	public void startCDATA() throws SAXException {
		echoText();
		nl();
		emit("START CDATA: ");
	}

	public void endCDATA() throws SAXException {
		echoText();
		nl();
		emit("END CDATA");
	}

	public void startEntity(String name) throws SAXException {
		// echoText();
		// nl();
		// emit("START ENTITY: ");
	}

	public void endEntity(String name) throws SAXException {
		// echoText();
		// nl();
		// emit("END ENTITY ");
	}

	public void startDTD(String name, String publicId, String systemId)
			throws SAXException {
	}

	public void endDTD() throws SAXException {
	}

	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		echoText();
		nl();
		emit("NOTATION ENTITY:  " + name);
	}

	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		echoText();
		nl();
		emit("UNPARSED ENTITY:  " + name);
	}

	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		echoText();
		nl();
		String resolved = null;

		if (systemId != null) {
			resolved = systemId;
		} else if (publicId != null) {
			resolved = publicId;
		}
		if (resolved != null) {
			emit("RESOLVE ENTITY:  " + resolved);
		}

		return null;
	}

	// ===========================================================
	// Utility Methods ...
	// ===========================================================
	private void echoText() throws SAXException {
		if (textBuffer == null) {
			return;
		}

		nl();
		emit("CHARS:   ");

		String s = "" + textBuffer;

		if (!s.trim().equals("")) {
			emit(s);
		}

		textBuffer = null;
	}

	// Wrap I/O exceptions in SAX exceptions, to
	// suit handler signature requirements
	private void emit(String s) throws SAXException {
		try {
			out.write(s);
			out.flush();
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	// Start a new line
	// and indent the next line appropriately
	private void nl() throws SAXException {
		String lineEnd = System.getProperty("line.separator");

		try {
			out.write(lineEnd);

			for (int i = 0; i < indentLevel; i++)
				out.write(indentString);
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

}
