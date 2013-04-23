package ro.upt.pcbe;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Client extends Thread{

	private String wantedDocument;
	private List<Document> obtainedDocuments;
	private String name;
	BureaucraticSystem system = BureaucraticSystem.getInstance();
	private CountDownLatch latch;

	public Client(String wantedDocument, String name) {
		this.wantedDocument = wantedDocument;
		this.name = name;
		obtainedDocuments = new ArrayList<Document>();
	}
	
	public String getClientName ()
	{
		return this.name;
	}

	public boolean hasNecessaryDocuments(Document document) {
		int obtainedNecessaryDocuments = 0;
		Iterator<Document> i = document.getNecessaryDocuments().iterator();
		while (i.hasNext()) {
			Document d = i.next();
			Iterator<Document> j = obtainedDocuments.iterator();
			while (j.hasNext()) {
				if (j.next().isNamed(d.getName())) {
					obtainedNecessaryDocuments++;
					break;
				}
			}
		}

		if (obtainedNecessaryDocuments == document.getNecessaryDocuments()
				.size())
			return true;
		else
			return false;
	}

	public String getWantedDocument() {
		return wantedDocument;
	}

	public List<Document> getObtainedDocuments() {
		return obtainedDocuments;
	}

	public void AddDocument(Document document) {
		obtainedDocuments.add(document);
		System.out.println("Client " + this.name + " has obtained Document "
				+ document.getName());
	}

	private void tryToObtainDocument(String documentName) throws InterruptedException {

		Office office = system.getOffice(documentName);
		Document document = office.issuesDocument(documentName);
		Counter counter = office.getACounter();
		counter.stayAtQueue(this);
		// wait at the counter
		synchronized (counter) {
			
			while (counter.getQueueLenght()>1)
			{

				counter.wait();
			}
		}
		System.out.println("\tClient " + getClientName() + " is done waiting for his turn at  counter " + counter.getNumber() + " of office: " + office.getNume());
		List<Document> necessaryDocuments = counter.releaseDocument(this,
				document);
		if (necessaryDocuments != null) {

			Iterator<Document> i = necessaryDocuments.iterator();
			while (i.hasNext()) {
				tryToObtainDocument(i.next().getName());
			}
			counter.releaseDocument(this, document);
		}
	}

	public void run() {

		try {
			latch.await();
			tryToObtainDocument(wantedDocument);
		} catch (InterruptedException e) {
			
			    System.out.println("Unfortunately I couldn't obtain my documents "+ this.name);
				e.printStackTrace();
				
		} 
		System.out.println("DONE "+ this.name);

	}

	// rc
	public Client(CountDownLatch latch,String wantedDocument, String name, BureaucraticSystem system) {
		this.wantedDocument = wantedDocument;
		this.name = name;
		this.system = system;
		obtainedDocuments = new ArrayList<Document>();
		this.latch = latch;
	}

}
