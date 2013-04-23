package ro.upt.pcbe;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

public class BureaucraticSystem {
	private static BureaucraticSystem instance = null;
	private List<Office> offices;

	private BureaucraticSystem() {
		offices = new ArrayList<Office>();
	}

	public void addOffice(Office office) {
		this.offices.add(office);
	}

	public static BureaucraticSystem getInstance() {
		if (instance == null) {
			instance = new BureaucraticSystem();
			ReadConfig();
		
		}

		return instance;
	}

	public Office getOffice(String documentName) {
		Iterator<Office> i = offices.iterator();
		while (i.hasNext()) {
			Office o = i.next();
			if (o.issuesDocument(documentName) != null)
				return o;
		}
		return null;
	}

	public List<Office> getOffices() {
		return offices;
	}

	private static void ReadConfig() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {

			InputStream xmlInput = new FileInputStream("config.xml");
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new SaxHandler();
			saxParser.parse(xmlInput, handler);

		} catch (Throwable err) {
			System.out.println("Couldn't read config file!! ");
			err.printStackTrace();
		}
	}

	
	

}
