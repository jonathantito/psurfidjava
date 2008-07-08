package client_server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
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
		fillLocationVector("Locations.txt");
		fillPalletVector("Pallets.txt");
		
		for(int i = 0; i < locationVec.size(); i++)
			System.out.println(locationVec.elementAt(i).hexID + " " + locationVec.elementAt(i).name);
		for(int i = 0; i < palletVec.size(); i++)
			System.out.println(palletVec.elementAt(i).hexID + " " + palletVec.elementAt(i).location);
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
				
				if(hexID.substring(0, 2).equalsIgnoreCase("32")){	
					System.out.println("Location Tag!");
					for(int i = 0; i < locationVec.size(); i++)
						if(hexID.equals(locationVec.elementAt(i)))
							currentLoc = locationVec.elementAt(i).name;
				}
				else if(hexID.substring(0, 2).equalsIgnoreCase("31")){
					System.out.println("Pallet Tag!");
					boolean palletFound = false;
					
					for(int i = 0; i < palletVec.size(); i++){
						if(hexID.equals(palletVec.elementAt(i))){
							palletVec.elementAt(i).location = currentLoc;
							palletFound = true;
						}
					}
					
					if(!palletFound){
						palletVec.addElement(new Pallet(hexID, currentLoc));
						updatePalletList("Pallets.txt");
						System.out.println("Pallet List updated!");
					}
				}
				else
					System.out.println("Unknown Tag");
				
				
				//System.out.println(hexID.substring(0, 2));					
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
	        
	        int numPallets = Integer.parseInt(in.readLine());
	        
		    if(numPallets != 0)    
	        	for(int i = 0; i < numPallets; i++){
		        	str = in.readLine().split("\t");
		            palletVec.addElement(new Pallet(str[0],str[1]));
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
	        
	        for(int i = 0; i < palletVec.size(); i++)
	        	palletList += palletVec.elementAt(i).hexID + "\t" + palletVec.elementAt(i).location + "\n";
	        
	        out.write(palletList);
	        out.close();
	    } catch (IOException e) {
	    }
	}
}
