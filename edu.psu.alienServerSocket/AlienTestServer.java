/*
 *  AlienTestServer.java
 *
 *  Created:	Jun 5, 2007
 *  Project:	RiFidi Emulator - A Software Simulation Tool for RFID Devices
 *  				http://www.rifidi.org
 *  				http://rifidi.sourceforge.net
 *  Copyright:	Pramari LLC and the Rifidi Project
 *  License:	Lesser GNU Public License (LGPL)
 *  				http://www.opensource.org/licenses/lgpl-license.html
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.alien.enterpriseRFID.reader.AlienClass1Reader;
import com.alien.enterpriseRFID.reader.AlienReaderException;

/**
 * @author Kyle
 * This is a TCPServer that is useful for testing Autonomous mode.  It starts up and listens to a specific port.  When it
 * recieves something it prints it out to the console as well as a file if the file name is supplied
 */
public class AlienTestServer {

	ServerSocket srvr;

	public AlienTestServer(InetSocketAddress addr) throws IOException {
		srvr = new ServerSocket();		
		srvr.bind(addr);
	}

	/**
	 * The main method
	 * @param args.  1)Local IP Address 2) Port 3) File to print results out too.  The file prints out some kind of bad character that 
	 * gedit doesn't open, so I've been using vi to delete the character.
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {

		//String ip = args[0];
		//String port = args[1];
		@SuppressWarnings("unused")
		BufferedWriter out = null;
		if (args.length>2) {
			out = new BufferedWriter(new FileWriter(args[2]));
		}

		InetAddress my_addr = InetAddress.getByName("127.0.0.5");
		AlienTestServer s = new AlienTestServer(new InetSocketAddress(
				my_addr, 20008));
		/*try {
			AlienClass1Reader reader = new AlienClass1Reader();
			reader.setConnection("127.0.0.1", 20000);
			reader.setUsername("alien");
			reader.setPassword("password");
			reader.open();
	
			//reader.setNotifyAddress("127.0.0.5", 20005);
		} catch (AlienReaderException e) {
				e.printStackTrace();
		}*/

		Socket skt = s.srvr.accept();
		System.out.print("Server has accepted a connection!\n");
		
		while (true) {
			
			int ch = skt.getInputStream().read();
			if(ch!=-1){
				System.out.print((char)ch);
			}
			if( skt.isClosed()){
				System.out.println("is closed");
			}
		}
	}
}

