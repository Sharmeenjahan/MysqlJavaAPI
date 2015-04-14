import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MysqlJavaAPI {

	public static void main(String[] args) throws SQLException {
	
		Converter c= new Converter();
		c.initMongoConnection();
		c.initSQLConnection();
		c.copyEmployees();
		c.closeSQLConnection();
	}
}	
