
import java.sql.Connection;
import java.sql.DriverManager;


public class MysqlJavaAPI {

	public static void main(String[] args) {
	
			//
			TestMysql db= new TestMysql();
			db.Clean();
			
			//take the start time to get the total time it takes to complete all threads.
		   	long begin=System.currentTimeMillis();
		   	
		   	
		   	
		 	int totalThreads=10;
		   	//creating an array of threads.
		   	Thread[] threads= new Thread[totalThreads];
		   	
			for (int i = 0; i < totalThreads; i++) {
				//creating each thread with a thread number.
				Thread t=new Thread("Thread #" + Integer.toString(i)){
					//inserting 1000 documents in each thread.
					public void run(){
						TestMysql db=new TestMysql();
						db.InsertRecords(100);
						//System.out.print(" miliseconds.");
					}
				};
				t.start();
				threads[i]=t;
			}
			
			//Get the total time it needs to complete the operation of all threads.
			for(int i=0 ; i<totalThreads;i++)
			{
				try
				{
				//wait for the thread to be completed.We need that to get the total time of the whole operation.	
				threads[i].join();
				}
				catch(InterruptedException e)
				{
				}
				
			}
			//getting the end time of the operation.
	   		long end=System.currentTimeMillis();
	   		//print the time difference of the total insertion.
	   		System.out.println();
	   		System.out.println("Total:" + Long.toString(end-begin) + " miliseconds.");
	   }

			
}
