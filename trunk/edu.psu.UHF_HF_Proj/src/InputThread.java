
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
				
				byte b[] = new byte[128];
				
				if(is.available() != 0){
					if(s.getPort() != 5555){
						System.out.println("Enter");
						String str = ReportXMLParser.parse(is);
						System.out.println(str);
					}
					else{
						while(is.available() != 0){					
							is.read(b);

							for(int i = 0; i < b.length; i++){
								if((char)b[i] != 0)							
									System.out.print((char)b[i]);
							}
							System.out.println();
						}
					}
				}

				//String str = ReportXMLParser.parse(is);
				//System.out.println(str);
				//srf.MessageTextArea.setText(str);

			}
		}
		catch (IOException ex) {
			//System.out.println(ex);
		}
	}
}
