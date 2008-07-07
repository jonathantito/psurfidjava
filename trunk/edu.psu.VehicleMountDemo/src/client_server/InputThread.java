package client_server;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
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
				byte[] b = new byte[4096];
				int numBytes = is.read(b);
				int numTags = numBytes/40;			
				String msg = "";
					
				for(int i = 0; i < numTags; i++){
					for(int j = 0; j < 40; j++)
						msg += (char)b[j];										
					msg += "\n";
				}
			
				System.out.println(msg);					
				srf.MessageTextArea.setText(msg);
			}
		}
		catch (IOException ex) {
			//System.out.println(ex);
		}
	}
}
