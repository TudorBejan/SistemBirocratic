package ro.upt.pcbe;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {

	// private boolean inOffice = false;
	// private boolean inDocument = false;
	private BureaucraticSystem system = BureaucraticSystem.getInstance();
	private Office currOffice;
	private Document currDocument;

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		switch (qName) {
		case "office": {
			currOffice = new Office(attributes.getValue("name"));
			currOffice.createCounters(new Integer(attributes
					.getValue("counters")));
			break;
		}
		case "document": {
			currDocument = new Document(attributes.getValue("name"));
			// inDocument = true;
			break;
		}
		case "necessaryDocument": {
			currDocument.AddNecessaryDocument(new Document(attributes
					.getValue("name")));
			break;
		}

		}
		// System.out.println(qName);
		// System.out.println(attributes.getValue("name"));

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		switch (qName) {
		case "office": {
			system.addOffice(currOffice);
			// inOffice = false;
			break;
		}
		case "document": {
			currOffice.AddIssuedDocument(currDocument);
			// inDocument = false;
			break;
		}

		}

	}

	public void characters(char ch[], int start, int length)
			throws SAXException {
	}

	public void ignorableWhitespace(char ch[], int start, int length)
			throws SAXException {
	}

}