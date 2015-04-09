
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MysqlJavaAPI {

	public static void main(String[] args) throws SQLException {
	
			//for insertion
			MysqlCrudActions db= new MysqlCrudActions();
		
			//for previous test{
			//db.Clean();
//			
//			//take the start time to get the total time it takes to complete all threads.
//		   	long begin=System.currentTimeMillis();
//		   	
//		   	
//		   	
//		 	int totalThreads=1;
//		   	//creating an array of threads.
//		   	Thread[] threads= new Thread[totalThreads];
//		   	
//			for (int i = 0; i < totalThreads; i++) {
//				//creating each thread with a thread number.
//				Thread t=new Thread("Thread #" + Integer.toString(i)){
//					//inserting 1000 documents in each thread.
//					public void run(){
//						MysqlCrudActions db=new MysqlCrudActions();
//						try {
							db.insertRecords();
						//for selection
							db.readRecords();
			
//						} //System.out.print(" miliseconds.");
// catch (SQLException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				};
//				t.start();
//				threads[i]=t;
//			}
//			
//			//Get the total time it needs to complete the operation of all threads.
//			for(int i=0 ; i<totalThreads;i++)
//			{
//				try
//				{
//				//wait for the thread to be completed.We need that to get the total time of the whole operation.	
//				threads[i].join();
//				}
//				catch(InterruptedException e)
//				{
//				}
//				
//			}
//			//getting the end time of the operation.
//	   		long end=System.currentTimeMillis();
//	   		//print the time difference of the total insertion.
//	   		System.out.println();
//	   		System.out.println("Total:" + Long.toString(end-begin) + " miliseconds."); } End previous
   }
//
//			
}
