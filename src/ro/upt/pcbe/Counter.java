package ro.upt.pcbe;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Counter extends Thread {
       
        private Random rand = new Random();
        private LinkedBlockingQueue<Client> queue = new LinkedBlockingQueue<Client>();
        private volatile boolean coffeeBreak = false;
        private int number;
        private Office office;
        
        public Counter (int number, Office office)
        {
        	this.number = number;
        	this.office = office;
        }
       
        public synchronized  void stayAtQueue (Client client)
        {
                queue.add(client);
                client.setPriority(MIN_PRIORITY);
                System.out.println("Client " + client.getClientName() + " is waiting for his turn at a counter " + this.number + " of office: " + office.getNume());
                //Thread.yield();
                //System.out.println("Client " + client.() + "is at the queue at a counter");
        }
       
        public int getNumber() {
			return number;
		}

		public synchronized int getQueueLenght ()
        {
                return queue.size();
        }

        public void run ()
        {
                while (true)
                {
                	
            		
            		if(rand.nextInt(100)<10){     // pauza de cafea la momente neprevazute
            			
            			try {
            						this.coffeeBreak();
            			} catch (InterruptedException e) {
            						System.out.println("No coffee break for us!!");	
            			}
            		}
            		else
            		{
                    	try
                        	{
                                // take first client from queue and resume execution of client
                                
                    			Client client = queue.take();
                    			synchronized (this) {
                    				System.out.println("\ttaking Client "+ client.getClientName() + " from queue at counter " + this.number + " " + office.getNume());
                                client.setPriority(MAX_PRIORITY);
                                this.notifyAll();
                                }
                               
                               
                               
                        	}
                        catch (InterruptedException e)
                        {
                                e.printStackTrace();
                        }
                    }
                }
                //}
        }
       
        //toate ghiseele se deschid la aceeasi ora
        public synchronized void getReadyToStart() throws InterruptedException{
                                                this.wait();
        }
       
        //anunta clientii ca ghiseul este disponibil sa preia cereri
        public synchronized void startWorking() throws InterruptedException{
                                                this.notifyAll();
        }
       
        //eliberarea documentului
        public synchronized List<Document> releaseDocument(Client client, Document document) {

                if (client.hasNecessaryDocuments(document))
                {
                        client.AddDocument(document);
                        return null;
                }
                else return document.getNecessaryDocuments();
        }
       
        // pauza de cafea
        public synchronized void coffeeBreak() throws InterruptedException{
                // angajatii i-au pauza de "duration" secunde pt cafea
        	    //coffeeBreak = true;
                long duration = Math.abs((this.rand.nextLong())%2000)+1000;
                System.out.println("Coffee break at a counter " + this.number + " from " + this.office.getNume());
                Thread.sleep(duration);
                //coffeeBreak = false;
                System.out.println("End of coffee break at a counter "+ this.number + " from " + this.office.getNume());
        }
}