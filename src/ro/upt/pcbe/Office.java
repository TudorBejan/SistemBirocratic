package ro.upt.pcbe;

import java.util.*;

public class Office {

	private List<Document> issuedDocuments;
	private String name;
	private List<Counter> counters;

	public Office(String name) {
		this.name = name;
		this.issuedDocuments = new ArrayList<Document>();
		this.counters = new ArrayList<Counter>();
	}

	public void AddIssuedDocument(Document document) {
		issuedDocuments.add(document);
	}

	public List<Document> getIssuedDocuments() {
		return issuedDocuments;
	}

	public String getNume() {
		return name;
	}

	public void createCounters(int number) {
		for (int i = 0; i < number; i++)
			counters.add(new Counter(i,this));
	}
  
	//rc
	public Counter getACounter() {
		Counter minQueueCounter = counters.get(0);
		Iterator<Counter> itCounter = this.getCounters().iterator();
		while(itCounter.hasNext()){
			Counter auxCounter = itCounter.next();
			if(auxCounter.getQueueLenght() == 0 || (auxCounter.getQueueLenght() < minQueueCounter.getQueueLenght())){
				minQueueCounter = auxCounter;
			}
		}
		return minQueueCounter;
	}

	public Document issuesDocument(String documentName) {
		Iterator<Document> i = issuedDocuments.iterator();
		while (i.hasNext()) {
			Document document = i.next();
			if (document.isNamed(documentName))
				return document;
		}
		return null;
	}

	// rc
	public List<Counter> getCounters() {
		return counters;
	}

	

}
