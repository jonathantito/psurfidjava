package client_server;

import java.util.Vector;

public class Pallet {
	String hexID;
	String location;
	Vector<String> path = new Vector<String>();
	
	public Pallet(String hexID, String location, String path){
		this.hexID = hexID;
		this.location = location;
		this.path.addElement(path);
	}
}
