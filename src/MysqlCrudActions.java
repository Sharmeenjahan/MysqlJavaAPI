import java.sql.*;
import java.io.*;
import java.util.*;

public class MysqlCrudActions {
	Connection con;
	private void initConnection(){
		   try{
				String url = "jdbc:mysql://localhost/";
				String dbName = "dbtest";
				String driver = "com.mysql.jdbc.Driver";
				String userName = "root"; 
				String password = "mysql";
				try {
				  		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbtest?user=root&password=mysql");
				 
				  } catch (Exception e) {
					  e.printStackTrace();
				  }	  
		   } finally{}
	}
	
	public void insertRecords() throws SQLException{
		
		 initConnection();
		 String sql1 = "INSERT INTO dept_manager(dept_no, manager_id, manager_name) values ('d006',01, 'abc' )";
		 //String sql2 = "INSERT INTO dept_manager(dept_no, manager_id, manager_name) values ('d010',01, 'abc' )"; 
         
         Statement statement = con.createStatement();
         
         statement.executeUpdate(sql1);  //works well
         //statement.executeUpdate(sql2);	//bad input 
	}
	
	public void readRecords() throws SQLException{
		
		initConnection();
		String sql1= "select * from dept_manager";
		Statement statement= con.createStatement();
		ResultSet rs = statement.executeQuery(sql1);
		rs.next();
		String id = rs.getString("dept_no");
		String sql2= "select * from departments where dept_no='" + id + "'";
		//con.close();
		Statement st= con.createStatement();
		ResultSet rs1 = st.executeQuery(sql2);
		String dept_no="";
		if (rs1.next())
		{
		dept_no= rs1.getString("dept_no");
		String name = rs1.getString("dept_name");
		System.out.println(dept_no);
		System.out.println(name);
		}
		else System.out.println("not found");
	}
}	
