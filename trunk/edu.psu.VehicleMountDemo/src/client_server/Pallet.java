package client_server;

public class Pallet {
	String hexID;
	String location;
	
	public Pallet(String hexID){
		this.hexID = hexID;
		this.location = "Unknown";
	}
	
	public Pallet(String hexID, String location){
		this.hexID = hexID;
		this.location = location;
	}
}
