package client_server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Vector;

public class InputThread extends Thread{
	Socket s;
	SocketReaderFrame srf;	
	Vector<Location> locationVec;
	String currentLoc;
	
	public InputThread(Socket s, SocketReaderFrame srf) {
		this.s = s;
		this.srf = srf;
		currentLoc = "Unknown";
		locationVec = new Vector<Location>();
		fillLocationVector("Locations.txt");
		
		for(int i = 0; i < locationVec.size(); i++)
			System.out.println(locationVec.elementAt(i).hexID + " " + locationVec.elementAt(i).name);
	}
	public void run(){
		try {
			while(true){
				InputStream is = s.getInputStream();
				byte[] b = new byte[120];
				is.read(b);			
				String msg = "";	

				for(int j = 0; j < 40; j++)
					msg += (char)b[j];										
				
				String hexID = msg.substring(10, 34);
				for(int i = 0; i < locationVec.size(); i++)
					if(hexID.equals(locationVec.elementAt(i)))
						currentLoc = locationVec.elementAt(i).name;
					
				
				
				
				System.out.println(hexID);					
				srf.MessageTextArea.setText(msg);
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
	        for(int i = 0; i < numLocations; i++){
	        	str = in.readLine().split("\t");
	            locationVec.addElement(new Location(str[0],str[1]));
	        }
	        in.close();
	    } catch (IOException e) {
	    	System.out.println("Error Reading from file!");
	    }
	}
}
