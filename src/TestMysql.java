import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.*;

public class TestMysql {
	Connection con;
	
	
   private void initConnection(){
	   try{
			//String url = "jdbc:mysql://localhost:3306/";
			String url = "jdbc:mysql://localhost/";
			  String dbName = "dbtest";
			  String driver = "com.mysql.jdbc.Driver";
			  String userName = "root"; 
			  String password = "mysql";
			  try {
			  //Class.forName(driver).newInstance();
			  //Connection conn = DriverManager.getConnection(url+dbName, userName, password);
			  con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbtest?user=root&password=mysql");
			  //System.out.println("Connection successful");
			  
			  } catch (Exception e) {
			  e.printStackTrace();
			  }


		}finally{}

   }
  
  //Insert documents into the database
  //@param number of documents we wish to insert.
 
   public void InsertRecords(int totalRecords){
	   
        try {
        	long begin= System.currentTimeMillis();
        	//initialize connection.
        	initConnection();
            for(int i=0; i<totalRecords; i++){
            String sql = "INSERT INTO t1(name, phone) values ('Tom'," + Integer.toString(i) + ")";
            Statement statement = con.createStatement();
             
            statement.executeUpdate(sql);
                       }     
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " "+ Long.toString(end-begin) + " miliseconds.");

                        
           // conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
	   }
   
   //to print records. still in progress...
   public void ReadRecords(){
	   
       
   }
   
   //remove all the data from database.
   public void Clean(){
	  try{ 
		  //initializing connection.
		  initConnection();
	   String sql = "TRUNCATE t1";
       Statement statement = con.createStatement();
       statement.executeUpdate(sql);
       
       
       // conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } 
	   
   }


}
