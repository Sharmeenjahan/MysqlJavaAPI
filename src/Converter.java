
import java.sql.*;
import java.util.Date;

import org.bson.types.ObjectId;

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
	DBCollection dColl, dmColl, eColl, tColl, sColl;	//collection variable to get the tables from database.
	
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
  	
	public void copyDepartments(){
		try {
		// Run a select query of all entries in departments table from MySQL
		Statement statement = con.createStatement();
		ResultSet res = statement.executeQuery("SELECT * FROM departments");
		
		// Create new table/collection of departments
		dColl= db.getCollection("departments");
		dColl.remove(new BasicDBObject());
		BasicDBObject copyDocument = new BasicDBObject();
		// Iterate through the departments, capture the information, and copy it to MongoDB
		while(res.next()) {
		String dept_no = res.getString("dept_no");
		String dept_name = res.getString("dept_name");
		

		copyDocument.put("dept_no", dept_no);
		copyDocument.put("dept_name", dept_name);
		dColl.insert(copyDocument);
		
		dept_name= "";
		copyDocument.clear();
		
		}
		
		res.close();
		res = null;
		copyDocument = null;
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void copyEmployees(){
		try {
		// Run a select query of all entries in departments table from MySQL
		Statement statement = con.createStatement();
		ResultSet res = statement.executeQuery("SELECT * FROM employees");
		
		// Create new table/collection of departments
		eColl= db.getCollection("employees");
		eColl.remove(new BasicDBObject());
		//sColl.remove(new BasicDBObject());
		//tColl.remove(new BasicDBObject());
		sColl= db.getCollection("salaries");
		tColl=db.getCollection("titles");
		
		int emp_no;
		Date birthDate, hireDate;
		String firstName, lastName;
		Object gender = null;
		BasicDBObject copyDocument = new BasicDBObject();
		
		// Iterate through the departments, capture the information, and copy it to MongoDB
		while(res.next()) {
			emp_no = res.getInt("emp_no");
			birthDate = res.getDate("birth_date");
			firstName = res.getString("first_name");
			lastName = res.getString("last_name");
			gender = res.getObject("gender");
			hireDate = res.getDate("hire_date");
			
			copyDocument.put("emp_no", emp_no);
			copyDocument.put("birth_date", birthDate);
			copyDocument.put("first_name", firstName);
			copyDocument.put("last_name", lastName);
			copyDocument.put("gender", gender);
			copyDocument.put("hire_date", hireDate);
			eColl.insert(copyDocument);	
			
			// Next, convert the current employee's job title and salary
			ObjectId id = (ObjectId) copyDocument.get("_id"); //Use this ID for reference in the next two methods
			copyTitle(emp_no, id);
			copySalary(emp_no, id);
			
			firstName = "";
			lastName = "";
			copyDocument.clear();
			
		}
		copyDocument = null;
		res.close();
		res= null;
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		}

	/**
	* Copy into the titles collection based on
	* an employee's number and the Object ID to reference back to
	* the employees collection. Similar to copyEmployees().
	* @param empNum: The value of the employee's emp_no
	* @param id: The ObjectId used by the employee's document
	*/
	public void copyTitle(int empNum, ObjectId id){
		try {
			String title;
			BasicDBObject copyDocument = new BasicDBObject();
			
			
			//Search for the salary of an employee by their emp_no
			Statement statement = con.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM titles WHERE emp_no = " + Integer.toString(empNum));
			while(res.next()) {
				title = res.getString("title");
				Date from = res.getDate("from_date");
				Date to = res.getDate("to_date");
				
				
				
				
				copyDocument.put("emp_no", id);
				copyDocument.put("title", title);
				copyDocument.put("from_date", from);
				copyDocument.put("to_date", to);
				
				tColl.insert(copyDocument);
				
				copyDocument.clear();
				title = "";
			}
			copyDocument=null;
			res.close();
			res= null;
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	* Copy into the salaries collection based on
	* an employee's number and the Object ID to reference back to
	* the employees collection. Similar to copyEmployees().
	* @param empNum: The value of the employee's emp_no
	* @param id: The ObjectId used by the employee's document
	*/
	public void copySalary(int empNum, ObjectId id){
		try {
			String salary;	
			BasicDBObject copyDocument = new BasicDBObject();
			
			
			//Search for the salary of an employee by their emp_no
			Statement statement = con.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM salaries WHERE emp_no = " + Integer.toString(empNum) );
			
			while(res.next()){
				salary = res.getString("salary");
				Date from = res.getDate("from_date");
				Date to = res.getDate("to_date");
				
				
				
				
				copyDocument.put("emp_no", id);
				copyDocument.put("salary", salary);
				copyDocument.put("from_date", from);
				copyDocument.put("to_date", to);
				sColl.insert(copyDocument);
				
				copyDocument.clear();
				salary = "";
				
			}
			copyDocument=null;
			res.close();
			res=null;
		}
		catch (Exception e){
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
	/**
	* Removes employee database from Mongo
	*/
	public void removeDB(){
	try {
	db.dropDatabase();
	}
	catch (Exception e) {
	System.out.println(e.getMessage());
	}
	}

}

   
