
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author John Cutsavage
 * @author Sharmeen Jahan
 * @author Drew Whittaker
 * 
 * This API is used to test the different performance
 * aspects of MySQL, as well as the conversion from a MySQL
 * database into a Mongo database.
 */
public class MysqlJavaAPI {

	public static void main(String[] args) throws SQLException {
	
		Converter c= new Converter();
		c.removeDB();
		c.initMongoConnection();
		//c.Clean();
		c.initSQLConnection();
		
		c.copyDepartments();
		c.copyEmployees();
		
		MyqlPerformance performance = new MysqlPerformance();
		
		performance.crudTests();
	}
}
