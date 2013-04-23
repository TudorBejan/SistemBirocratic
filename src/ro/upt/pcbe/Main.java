package ro.upt.pcbe;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

public class Main {
	
	
	public static void main (String argv[]) throws InterruptedException
	{
		BureaucraticSystem system = BureaucraticSystem.getInstance();
		
		CountDownLatch latch = new CountDownLatch(1);
		
		new Client(latch,"document1","client1",system).start();
		new Client(latch,"document4","client2",system).start();
		new Client(latch,"document3","client3",system).start();
		new Client(latch,"document1","client4",system).start();
		
		
		Iterator<Office> i = system.getOffices().iterator();
		while (i.hasNext())
		{
			Iterator<Counter> j = i.next().getCounters().iterator();
			while (j.hasNext()){
				j.next().start();
			}
		}
		
		
		latch.countDown();
		
		System.out.println("8:00 AM -> OPEN and waiting for clients... ");
				
	}
	

}
