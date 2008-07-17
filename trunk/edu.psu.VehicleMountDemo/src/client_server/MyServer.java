package client_server;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	int port;
	String ip;
	SocketReaderFrame srf;
	String dash = "\n--------------------------------------------------\n";
	
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
			srf.logTextArea.append("Collecting tags on:\nIP: " + ip + "\nPort: " + port + dash);
			InputThread it = new InputThread(s, srf);
			it.start();
		}
	}
}
