package client_server;
import java.io.*;
import java.net.*;
public class InputThread extends Thread{
	Socket s;
	SocketReaderFrame srf;	

	public InputThread(Socket s, SocketReaderFrame srf) {
		this.s = s;
		this.srf = srf;
	}
	public void run(){
		try {
			while(true){

				InputStream is = s.getInputStream();
				String str = ReportXMLParser.parse(is);
				System.out.println(str);
				srf.MessageTextArea.setText(str);

			}
		}
		catch (IOException ex) {
			//System.out.println(ex);
		}
	}
}
