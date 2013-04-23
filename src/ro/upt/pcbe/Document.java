package ro.upt.pcbe;

import java.util.ArrayList;
import java.util.List;

public class Document {

	private String name;
	private List<Document> necessaryDocuments;
	//private String ClientName;
	private Office office;

	public Document(String name, List<Document> necessaryDocuments,
			Office office) {
		this.name = name;
		this.necessaryDocuments = necessaryDocuments;
		this.office = office;
	}

	public Document(String name, Office office) {
		this.name = name;
		this.office = office;
		this.necessaryDocuments = new ArrayList<Document>();
	}

	public Document(String name) {
		this.name = name;
		this.necessaryDocuments = new ArrayList<Document>();
	}

	public void AddNecessaryDocument(Document document) {
		this.necessaryDocuments.add(document);
	}

	public List<Document> getNecessaryDocuments() {
		return necessaryDocuments;
	}

	public String getName() {
		return name;
	}

	// TODO: getOffice
	public Office getOffice() {
		return office;
	}

	//public void fillClientName(String ClientName) {
		//this.ClientName = ClientName;
	//}

	public boolean isNamed(String documentName) {
		return this.name.equals(documentName);
	}

}
