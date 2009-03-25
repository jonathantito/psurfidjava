package client_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Driver {
	SocketReaderFrame srf;
	Connection wmsConnection;
	String dash = "\n--------------------------------------------------\n";
	
	public Driver(){
		// start both frames, socketreaderframe is not visible yet
		new ServerFrame(this);
		srf = new SocketReaderFrame();
		
		try {
			wmsConnection = createConnection();
			srf.logTextArea.append("Connected to " + "jdbc:mysql://localhost:3306/wms"
					+ dash);	
			srf.wmsConnection = wmsConnection;
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) throws Exception{
		new Driver();
	}
	
	//gets called when the ip and port are chosen 
	public void connect(String ip, int port){
		final int newport = port;
		final String newip = ip;
	
		srf.setVisible(true);

		Thread r = new Thread(){
			public void run(){
				new MyServer(newip,newport,srf);
			}
		};
		r.start();
	}
	
	public Connection createConnection() throws Exception {
		Connection connection = null;
		try {
			// Load the JDBC driver
			String driverName = "org.gjt.mm.mysql.Driver"; // MySQL MM JDBC driver
			Class.forName(driverName);

			// Create a connection to the database
			String serverName = "localhost:3306";
			String mydatabase = "wms";
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase; // a JDBC url
			String username = "root";
			String password = "";
			connection = DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// Could not find the database driver
		} catch (SQLException e) {
			e.printStackTrace();
			// Could not connect to the database
		} 
		return connection;
	}
}
