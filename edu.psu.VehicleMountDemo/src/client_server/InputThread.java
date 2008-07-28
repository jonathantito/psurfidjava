package client_server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class InputThread extends Thread{
	Socket s;
	SocketReaderFrame srf;	
	Vector<Location> locationVec;
	Vector<Pallet> palletVec;
	String currentLoc;

	public InputThread(Socket s, SocketReaderFrame srf) {
		this.s = s;
		this.srf = srf;
		currentLoc = "Unknown";
		locationVec = new Vector<Location>();
		palletVec = new Vector<Pallet>();	
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
					System.out.println(currentLoc);
					if(!currentLoc.equalsIgnoreCase("Unknown")){
						try {
							Statement st = srf.wmsConnection.createStatement();
							ResultSet rs = st.executeQuery("SELECT TAGHEXID FROM TAGS");

							while(rs.next()){
								String result = rs.getString(1);
								if(hexID.equalsIgnoreCase(result)){//found tag in table
									Statement st1 = srf.wmsConnection.createStatement();
									String cmd = "SELECT Desc FROM Locations WHERE LocHexID = \"" 
										+ currentLoc + "\""; 
									System.out.println(cmd);
									ResultSet rs1 = st1.executeQuery(cmd);

									while(rs1.next()){
										Statement st2 = srf.wmsConnection.createStatement();
										cmd = "UPDATE taglocations " +
										"SET LOCHEXID = \"" + currentLoc + 
										"\", LOCDESCRIPTION = \"" + rs1.getString(1) + 
										"\"WHERE TAGHEXID = \"" + hexID + "\"";								

										System.out.println(cmd);
										st2.executeUpdate(cmd);
									}
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					/*boolean palletFound = false;

					for(int i = 0; i < palletVec.size(); i++){
						if(hexID.equals(palletVec.elementAt(i).hexID)){
							palletVec.elementAt(i).location = currentLoc;

							if(!palletVec.elementAt(i).path.lastElement().equalsIgnoreCase(currentLoc)){
								palletVec.elementAt(i).path.addElement(currentLoc);
								updatePalletList("Pallets.txt");
							}
							palletFound = true;
						}
					}

					if(!palletFound){
						palletVec.addElement(new Pallet(hexID, currentLoc, currentLoc));
						updatePalletList("Pallets.txt");
					}*/
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

	public void fillPalletVector(String file){
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(file)));
			String[] str;	        
			String[] p;

			int numPallets = Integer.parseInt(in.readLine());

			if(numPallets != 0)    
				for(int i = 0; i < numPallets; i++){
					str = in.readLine().split("\t");
					p = str[2].split(">");
					String path = "";
					for(int j = 0; j < p.length; j++)
						path += p[j] + ">";

					palletVec.addElement(new Pallet(str[0],str[1], path));
				}

			in.close();
		} catch (IOException e) {
			System.out.println("Error Reading from pallet file!");
		}
	}

	public void updatePalletList(String file){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			String palletList = "" + palletVec.size() + "\n";

			for(int i = 0; i < palletVec.size(); i++){
				palletList += palletVec.elementAt(i).hexID + "\t" + palletVec.elementAt(i).location + "\t";

				for(int j = 0; j < palletVec.elementAt(i).path.size(); j++)
					palletList += palletVec.elementAt(i).path.elementAt(j) + ">";

				palletList += "\n";
			}        

			out.write(palletList);
			out.close();
		} catch (IOException e) {
		}
	}
}
