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
		
		try {
			Statement st = srf.wmsConnection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM TAGS");
			//rs.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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

				//if tag is a location, traverse location vector until found
				//	if found update location, ow ignore
				if(hexID.substring(0, 2).equalsIgnoreCase("32")){	
					for(int i = 0; i < locationVec.size(); i++)
						if(hexID.equals(locationVec.elementAt(i).hexID))
							currentLoc = locationVec.elementAt(i).name;
				}
				//pallet tag
				else if(hexID.substring(0, 2).equalsIgnoreCase("31")){
					boolean palletFound = false;

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
					}
				}
			}
		}
		catch (IOException ex) {
		}
	}

	public void fillLocationVector(String file){
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(file)));
			String[] str;	        

			int numLocations = Integer.parseInt(in.readLine());

			if(numLocations != 0)
				for(int i = 0; i < numLocations; i++){
					str = in.readLine().split("\t");
					locationVec.addElement(new Location(str[0],str[1]));
				}

			in.close();
		} catch (IOException e) {
			System.out.println("Error Reading from location file!");
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
