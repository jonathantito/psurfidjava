package client_server;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	int port;
	String ip;
	SocketReaderFrame srf;
	
	public MyServer(String ip, int port, SocketReaderFrame srf) {
		this.port = port;
		this.srf = srf;
		this.ip = ip;
		
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void connect() throws Exception{
		ServerSocket ss = new ServerSocket(port);
		while(true){
			Socket s = ss.accept();			
			System.out.println("Socket Accepted!");
			InputThread it = new InputThread(s, srf);
			it.start();
		}
	}
}
