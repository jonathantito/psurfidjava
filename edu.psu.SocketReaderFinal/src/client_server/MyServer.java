package client_server;
import java.net.*;
import java.util.Vector;
import java.io.InputStream;
public class MyServer {
	int port = 0;
	SocketReaderFrame srf;
	public MyServer(int port, SocketReaderFrame srf) {
		this.port = port;
		this.srf = srf;
		
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
