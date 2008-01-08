import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketReader {
	SocketReader s;
	SocketReaderFrame srf;
	
	ServerSocket srvr = new ServerSocket();
	Socket skt;
	InetAddress my_addr;
	InputStream is;
	OutputStream os;
	ReportXMLParser parser;
	
	boolean sendReports = false;
	
	//Constructor
	public SocketReader() throws IOException{
		srf = new SocketReaderFrame(this);
		parser = new ReportXMLParser(srf);
		initializeSocket();
		srf.startButton.setEnabled(true);
	}

	//Constructor
	public SocketReader(InetSocketAddress addr) throws IOException {
		srvr = new ServerSocket();		
		srvr.bind(addr);
	}

	// MAIN 
	public static void main(String args[])throws IOException{
		new SocketReader();	
	}

	// Read report from socket, then print to text area
	public void getReport() throws IOException{
		
			is = skt.getInputStream();
			os = skt.getOutputStream();	
		do{
			String result = ReportXMLParser.parse(is, os);
			srf.MessageTextArea.setText(result);
			System.out.println(result);
		} while(sendReports);
	}
		
	public void initializeSocket(){
		try {
			my_addr = InetAddress.getByName(srf.ipTextField.getText());
			s = new SocketReader(new InetSocketAddress
					(my_addr, Integer.parseInt(srf.portTextField.getText())));

			skt = s.srvr.accept();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("Server has accepted a connection!\n");
	}
}

