package client_server;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InputThread extends Thread{
	Socket s;
	SocketReaderFrame srf;	
	String currentLoc;

	public InputThread(Socket s, SocketReaderFrame srf) {
		this.s = s;
		this.srf = srf;
		currentLoc = "Unknown";	
	}
	public void run(){
		try {
			while(true){
				InputStream is = s.getInputStream();
				byte[] b = new byte[120];
				is.read(b);					//read tag from socket into b
				String msg = "";	

				//load contents of b into string
				for(int j = 0; j < 40; j++)
					msg += (char)b[j];										

				String hexID = msg.substring(10, 34);

				//location tag
				if(hexID.substring(0, 2).equalsIgnoreCase("32")){	
					try {
						Statement st = srf.wmsConnection.createStatement();
						ResultSet rs = st.executeQuery("SELECT LOCHEXID FROM LOCATIONS " +
								"WHERE LOCHEXID = \"" + hexID + "\"");

						//only change the location if the tag id is found in the DB
						while(rs.next()){
							String result = rs.getString(1);
							if(hexID.equalsIgnoreCase(result))
								currentLoc = hexID;								
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				//pallet tag
				else if(hexID.substring(0, 2).equalsIgnoreCase("31")){
					//System.out.println(currentLoc);
					if(!currentLoc.equalsIgnoreCase("Unknown")){
						try {
							Statement st = srf.wmsConnection.createStatement();
							ResultSet rs = st.executeQuery("SELECT * FROM TAGS");

							while(rs.next()){
								String resultHexID = rs.getString(1);
								if(hexID.equalsIgnoreCase(resultHexID)){//found tag in table
									Statement st1 = srf.wmsConnection.createStatement();
									String cmd = "SELECT * FROM LOCATIONS WHERE LOCHEXID = \"" 
										+ currentLoc + "\""; 
									//System.out.println(cmd);
									ResultSet rs1 = st1.executeQuery(cmd);

									while(rs1.next()){//will exec if location found in table
										Statement st2 = srf.wmsConnection.createStatement();
										
										if(!rs.getString(4).equalsIgnoreCase(currentLoc)){
											cmd = "INSERT into tagLocations (lochexid,taghexid,locdate,loctime) " +
											"VALUES(\"" + currentLoc + "\", \"" + hexID + 
											"\" ,CURDATE(), CURTIME())";
											System.out.println(cmd);
											st2.executeUpdate(cmd);
										
											cmd = "UPDATE TAGS " +
											"SET CURLOCID = \"" + currentLoc + 
											"\" WHERE TAGHEXID = \"" + hexID + "\"";
											st2.executeUpdate(cmd);
										}	
									}
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		catch (IOException ex) {
		}
	}

	public void fillLocationVector(){
		try {
			Statement st = srf.wmsConnection.createStatement();
			ResultSet rs = st.executeQuery("SELECT DISTINT LOCHEXID FROM LOCATIONS");

			while(rs.next()){
				//locationVec.addElement(obj)
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
