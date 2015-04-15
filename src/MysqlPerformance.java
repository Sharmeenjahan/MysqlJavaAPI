import java.sql.*;
import java.lang.Runtime;

/**
 * @author John Cutsavage
 * @author Sharmeen Jahan
 * @author Drew Whittaker
 * 
 * Tests memory and timing performance of CRUD operations in a MySQL server.
 */
public class MysqlPerformance {
	
	Connection con;
	
	/**
	 * Initialize a connection to MySQL server.
	 * Creates a new database called performance_test.
	 */
	public void initConnection(){
		try{
			String url = "jdbc:mysql://localhost/";
			String driver = "com.mysql.jdbc.Driver";
			String userName = "root"; 
			String password = "mysql";
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userName, password);
				
				Statement stmt = con.createStatement();
				
				String createDB = "CREATE DATABASE performance_test;";
				stmt.executeUpdate(createDB);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}	  
		   } 
		finally{}
	}
	
	public void crudTests(){
		createTest();
		readTest();
		updateTest();
		deleteTest();
	}
	
	/**
	 * Performance test for timing and memory usage for
	 * create procedures.
	 */
	public void createTest(){
		try{
			Statement sqlStatement = con.createStatement();
			long startTime = System.currentTimeMillis();
			
			// Create a new table with only one column for now
			String createTable = "CREATE TABLE table1(id int(3) NOT NULL, PRIMARY_KEY(id));";
			sqlStatment.executeUpdate(createTable);
			
			// Insert 100 records
			for(int i = 1; i <= 100; i++){
				String insertData = "INSERT INTO table1 VALUES ("+ i + ");";
				sqlStatement.executeUpdate(insertData);
			}
			
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime();
			
			System.out.println("Current time to execute insertions:" + runTime);
			
			Runtime runtime = Runtime.getRuntime();
			runtime.gc();	// Runs java garbage collectiong
			
			// Total memory used is total memory - free memory
			long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
			
			System.out.println("Total memory used by insertions (in bytes): " + usedMemory);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * Performance test for timing and memory usage for
	 * read procedures.
	 */
	public void readTest(){
		try{
			Statement sqlStament = con.createStatement();
			long startTime = System.currentTimeMillis();
			
			// Read all of the data in the table
			String readAll = "SELECT * FROM table1;";
			sqlStatement.executeUpate(readAll);
			
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			
			System.out.println("Current time to execute reads:" + runTime);
			
			Runtime runtime = Runtime.getRuntime();
			runtime.gc();	// Runs java garbage collection
			
			// Get total memory used by subtracting the free memory from total memory
			long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
			
			System.out.println("Total memory used by reads (in bytes): " + usedMemory);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Performance test for timing and memory usage for
	 * update procedures.
	 */
	public void updateTest(){
		try{
			Statement sqlStament = con.createStatement();
			long startTime = System.currentTimeMillis();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Performance test for timing and memory usage for
	 * delete procedures.
	 */
	public void deleteTest(){
		try{
			Statement sqlStament = con.createStatement();
			long startTime = System.currentTimeMillis();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
