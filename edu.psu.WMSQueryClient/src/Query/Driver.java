package Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Driver {
	QueryClient qc;
	Connection wmsConnection;
	
	public Driver(){
		try {
			wmsConnection = createConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		qc = new QueryClient(wmsConnection);
	}
	public static void main(String[] args) throws Exception{
		new Driver();
	}
		
	public Connection createConnection() throws Exception {
		Connection connection = null;
		try {
			// Load the JDBC driver
			//String driverName = "org.gjt.mm.mysql.Driver"; // MySQL MM JDBC driver
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);

			// Create a connection to the database
			String serverName = "localhost:3306";
			String mydatabase = "wms";
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase; // a JDBC url
			String username = "root";
			String password = "";
			connection = DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException e) {
			// Could not find the database driver
			e.printStackTrace();
		} catch (SQLException e) {
			// Could not connect to the database
			e.printStackTrace();
		} 
		return connection;
	}
}
