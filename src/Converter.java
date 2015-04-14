
import java.sql.*;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
*
*@author Drew Whittaker
* @author John Cutsavage
* @author Sharmeen Jahan
*
* This class implements both MySQL and MongoDB APIs
* in order to convert an example employee MySQL database
* into a MongoDB database.
*
*/
public class Converter {
	
	Connection con;
	
	/**
	* Creates a new mysql connection on the default host. Provide
	* the connection with your username and password to log into MySQL
	*
	* @param username: The user's username.
	* @param password: The user's password.
	*
	*/
	public void initSQLConnection(){
		   try{
				String url = "jdbc:mysql://localhost/";
				String dbName = "employees";
				String driver = "com.mysql.jdbc.Driver";
				String userName = "root"; 
				String password = "mysql";
				try {
				  		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employees?user=root&password=mysql");
				 
				  } catch (Exception e) {
					  e.printStackTrace();
				  }	  
		   } finally{}
	}
	

	MongoClient mongoClient;
	DB db;
	DBCollection eColl;	//collection variable to get the tables from database.
	
	//Initialize the connection and get the database from mongodb instance.
   public void initMongoConnection(){
      try{   
    	  // To connect to mongodb server
         mongoClient = new MongoClient( "localhost" , 27017 );
         
         // Now use your databases
         db = mongoClient.getDB( "employees" );
        
        
         //dColl = db.getCollection("departments");
         
        // mColl = db.getCollection("dept_manager");
      }
      catch (Exception e)
      {
    	  System.out.println(e.getMessage());
      }
   }
  
	
	
	public void copyEmployees(){
		try {
		// Run a select query of all entries in employees table from MySQL
		Statement statement = con.createStatement();
		ResultSet res = statement.executeQuery("SELECT * FROM employees");
		
		// Create new table/collection of employees
		eColl= db.getCollection("employees");
		
		// Iterate through the employees, capture their information, and copy it to MongoDB
		while(res.next()) {
		int empNum = res.getInt("emp_no");
		Date birthDate = res.getDate("birth_date");
		String firstName = res.getString("first_name");
		String lastName = res.getString("last_name");
		Object gender = res.getObject("gender");
		Date hireDate = res.getDate("hire_date");

		BasicDBObject copyDocument = new BasicDBObject();

		copyDocument.put("emp_no", empNum);
		copyDocument.put("birth_date", birthDate);
		copyDocument.put("first_name", firstName);
		copyDocument.put("last_name", lastName);
		copyDocument.put("gender", gender);
		copyDocument.put("hire_date", hireDate);
		eColl.insert(copyDocument);
		}
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		}

	
	/**
	* Closes the MySQL connection.
	*/
	public void closeSQLConnection(){
		try{
		con.close();
		}
		catch(SQLException e) {
		e.printStackTrace();
		}
	}
	
}

   
