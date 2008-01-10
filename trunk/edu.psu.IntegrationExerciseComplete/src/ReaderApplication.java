import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.alien.enterpriseRFID.discovery.AlienDiscoverySocketException;
import com.alien.enterpriseRFID.discovery.DiscoveryItem;
import com.alien.enterpriseRFID.discovery.DiscoveryListener;
import com.alien.enterpriseRFID.discovery.NetworkDiscoveryListenerService;
import com.alien.enterpriseRFID.notify.Message;
import com.alien.enterpriseRFID.notify.MessageListener;
import com.alien.enterpriseRFID.notify.MessageListenerService;
import com.alien.enterpriseRFID.reader.AlienClass1Reader;
import com.alien.enterpriseRFID.reader.AlienReaderException;
import com.alien.enterpriseRFID.tags.Tag;

public class ReaderApplication implements MessageListener, DiscoveryListener {

	boolean packFrame = false;
	public ReaderFrame f;

	AlienClass1Reader reader;
	int mlsPort = 4000;
	MessageListenerService mls;
	NetworkDiscoveryListenerService ndls;
	Tag tagList[];
	ServerSocket srvr;
	Socket skt;

	String cmd = "";
	String parameters = "";

	//Construct the application
	public ReaderApplication() {
		f = new ReaderFrame("Reader Frame");
		mls = new MessageListenerService(mlsPort);	
	}
	
	public ReaderApplication(InetSocketAddress addr) throws IOException {
		srvr = new ServerSocket();
		srvr.bind(addr);
	}

	public void ExecuteCommand(String tempCommand){
		/*
		 * Command Format:
		 * textOneWord
		 * textOneWord = parameter,parameter
		 * textOneWord = parameter
		 * 
		 * EXAMPLE:
		 * getTaglist
		 * AntennaSequence = 0,1
		 * ExternalOutput = 1
		 */

		cmd = "";
		parameters = "";
		tempCommand = tempCommand.replace(" ", "");
		setCommandAndParameters(tempCommand);

		if(cmd.equalsIgnoreCase("Connect")){
			try {
				/*InetAddress my_addr = InetAddress.getByName("127.0.0.5");
				ReaderApplication ra = new ReaderApplication(new InetSocketAddress(
						my_addr, 20006));*/
				
				reader = new AlienClass1Reader();
				//set the connection to the reader by retrieving the IP address 
				// from 'IPTextField' and the Port from 'PortTextField' which will 
				// need parsed
				reader.setConnection(f.IPTextField.getText(), Integer.parseInt(f.PortTextField.getText()));
				reader.setUsername("alien");
				reader.setPassword("password");
				reader.open();
				
				/*skt = ra.srvr.accept();
				displayText("Server has accepted a connection!\n");*/

				// use the function displayText(String); displaying which reader we are connected to
				displayText("Connected to Reader: " + reader.getIPAddress() + " \n");
		
				// CONFIG SETTINGS			
				//reader.setFactorySettings();
				reader.setNotifyMode(AlienClass1Reader.OFF);
				reader.setAutoMode(AlienClass1Reader.OFF);
				reader.setAntennaSequence("0,1");
				reader.setRFAttenuation(30);
				reader.setTagType(16);
				reader.setPersistTime(5);
				reader.setAcquireMode("Inventory");
				reader.setAutoAction("Acquire");
			
				// set the Notify address on the reader using this host's IPAddress, and 
				// the port number that the service is listening on
				reader.setNotifyAddress(InetAddress.getLocalHost().getHostAddress(), mlsPort); 
				reader.setNotifyFormat(AlienClass1Reader.XML_FORMAT);
				reader.setNotifyTrigger("TrueFalse"); 
			
				//set External output
				reader.setExternalOutput(0);
				reader.setAutoTruePause(2000);
				reader.setAutoFalsePause(2000);
				reader.setAutoStartTrigger(0,0);
				reader.setAutoStopTrigger(0,1);
				reader.setAutoTrueOutput(1);
				reader.setAutoFalseOutput(4);
				reader.setAutoWorkOutput(2);
				
			}catch (Exception ex) {
				displayText("Failed to connect or configure reader\n");
				ex.printStackTrace();
			}
		}

		else if(cmd.equalsIgnoreCase("Disconnect")){
			try {
				ExecuteCommand("automode = off");
				//Close the connection to the reader
				reader.close();
				// use the function displayText(String); to display that the previous command was successful
				displayText("Disconnected from the Reader: " + reader.getIPAddress() + " \n");				
			} 
			catch (AlienReaderException e1) {
				displayText("Failed to disconnect to Reader\n");
			}
		}

		else if(cmd.equalsIgnoreCase("Taglist")){
			try {
				//get the taglist from the reader and set it equal to 'tagList'
				// which is already defined as an array of tags above
				tagList = reader.getTagList();
				//write an if statement the outputs a message to 'MessageTextArea' if tagList is null
				if (tagList == null) {
					displayText("No Tags Found\n");
				} 
				//write an else statement to print out the tags in the tag list
				else {
					displayText("Tag(s) found:\n");
					//write a for loop from zero to the length of the taglist
					for (int i=0; i<tagList.length; i++) {
						//declare a Tag type variable called tag (lowercase)
						// and set it equal to tagList[index]
						Tag tag = tagList[i];
						//display 'tag' using displayText(String);
						displayText("ID:" + tag.getTagID() +
								", Discovered:" + tag.getDiscoverTime() +
								", Last Seen:" + tag.getRenewTime() +
								", Antenna:" + tag.getAntenna() +
								", Reads:" + tag.getRenewCount() + "\n");
						//set 'tag's renew count to 0
						tag.setRenewCount(0);
					}
				}
			} 
			catch (AlienReaderException e) {
				displayText("Could not execute command\n");
			}
		}

		else if(cmd.equalsIgnoreCase("AutoMode")){
			if(parameters.equalsIgnoreCase("on")){
				try {
					//create a new instance of the MessageListenerService called 'mls' 
					// which is previously declared above.  Pass the parameter mlsPort to
					// the constructer 
					if(!mls.isRunning()){
						//set the Message Listener for 'mls'.  Pass it the parameter 'this'
						mls.setMessageListener(this);
						//start the mls service
						mls.startService();
						System.out.println("entered mlsDefined");
					}			

					//set the notify mode on the reader to 'AlienClass1Reader.ON'
					reader.setNotifyMode(AlienClass1Reader.ON);
					//set the auto mode on the reader to 'AlienClass1Reader.ON'
					reader.setAutoMode(AlienClass1Reader.ON);

					//tell the thread to sleep for 1000
					Thread.sleep(1000);
					
					//display a message using displayText(String); saying the listener has started
					displayText("Message Listener has Started\n");

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (AlienReaderException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(parameters.equalsIgnoreCase("off")){
				try {
					Thread.yield();
					//set the reader's automode to AlienClass1Reader.OFF
					reader.setAutoMode(AlienClass1Reader.OFF);
					//set the notify mode on the reader to 'AlienClass1Reader.OFF'
					reader.setNotifyMode(AlienClass1Reader.OFF);
					//use the function displayText(String); to display that the previous command was successful
					displayText("AutoMode = " + reader.getAutoMode() + "\n");
				} 
				catch (AlienReaderException e) {
					displayText("Parameter(s) not valid\n");
				}
			}	
		}		

		else if(cmd.equalsIgnoreCase("AutoAction")){
			try {
				// Set the AutoAction by passing it 'parameters'
				reader.setAutoAction(parameters);
				// use the function displayText(String); to display that the previous command was successful
				displayText("AutoAction = " + reader.getAutoAction() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}
		}		

		else if(cmd.equalsIgnoreCase("PersistTime")){
			try {
				// Set the PersistTime by parsing 'parameters' as an integer
				reader.setPersistTime(Integer.parseInt(parameters));
				// use the function displayText(String); to display that the previous command was successful
				displayText("PersistTime = " + reader.getPersistTime() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}
		}	

		else if(cmd.equalsIgnoreCase("AcquireMode")){
			try {
				// Set the AcquireMode by passing it 'parameters'
				reader.setAcquireMode(parameters);
				// use the function displayText(String); to display that the previous command was successful
				displayText("AcquireMode = " + reader.getAcquireMode() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}	
		}	

		else if(cmd.equalsIgnoreCase("ExternalOutput")){
			try {
				// Set the ExternalOutput by parsing 'parameters' as an integer
				reader.setExternalOutput(Integer.parseInt(parameters));
				// use the function displayText(String); to display that the previous command was successful
				displayText("External Output = " + reader.getExternalOutput() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}	
		}

		else if(cmd.equalsIgnoreCase("AutoTrueOutput")){
			try {
				// Set the AutoTrueOutput by parsing 'parameters' as an integer 
				reader.setAutoTrueOutput(Integer.parseInt(parameters));
				// use the function displayText(String); to display that the previous command was successful
				displayText("AutoTrueOutput = " + reader.getAutoTrueOutput() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}
		}	

		else if(cmd.equalsIgnoreCase("AutoFalseOutput")){
			try {
				// Set the AutoFalseOutput by parsing 'parameters' as an integer 
				reader.setAutoFalseOutput(Integer.parseInt(parameters));
				// use the function displayText(String); to display that the previous command was successful
				displayText("AutoFalseOutput = " + reader.getAutoFalseOutput() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}
		}

		else if(cmd.equalsIgnoreCase("AutoStartTrigger")){
			try {
				// Set the AutoStartTrigger.  
				// It's entered into the command line in the form of: AutoStartTrigger 0,0
				// It takes two integer parameters
				// The variable 'parameters' is in String form, you will need to separate
				//     the two variables and parse them.
				reader.setAutoStartTrigger(Integer.parseInt("" + parameters.charAt(0)),
						Integer.parseInt("" + parameters.charAt(2)));
				// use the function displayText(String); to display that the previous command was successful
				displayText("AutoStartTrigger = " + parameters + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}	
		}			

		else if(cmd.equalsIgnoreCase("AutoStopTrigger")){
			try {
				// Set the AutoStopTrigger.  
				// It's entered into the command line in the form of: AutoStopTrigger 0,0
				// It takes two integer parameters
				// The variable 'parameters' is in String form, you will need to separate
				//     the two variables and parse them.
				reader.setAutoStopTrigger(Integer.parseInt("" + parameters.charAt(0)),
						Integer.parseInt("" + parameters.charAt(2)));
				// use the function displayText(String); to display that the previous command was successful
				displayText("AutoStopTrigger = " + parameters + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}	
		}			

		else if(cmd.equalsIgnoreCase("AutoWorkOutput")){
			try {
				// Set the AutoWorkOutput by parsing 'parameters' as an integer 
				reader.setAutoWorkOutput(Integer.parseInt(parameters));
				// use the function displayText(String); to display that the previous command was successful
				displayText("AutoWorkOutput = " + reader.getAutoWorkOutput() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}
		}	

		else if(cmd.equalsIgnoreCase("AutoTruePause")){
			try {
				// Set the AutoTruePause by parsing 'parameters' as an integer 
				reader.setAutoTruePause(Integer.parseInt(parameters));
				// use the function displayText(String); to display that the previous command was successful
				displayText("AutoTruePause = " + reader.getAutoTruePause() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}
		}	

		else if(cmd.equalsIgnoreCase("AutoFalsePause")){
			try {
				// Set the AutoFalsePause by parsing 'parameters' as an integer 
				reader.setAutoFalsePause(Integer.parseInt(parameters));
				// use the function displayText(String); to display that the previous command was successful
				displayText("AutoFalsePause = " + reader.getAutoFalsePause() + "\n");
			} 
			catch (AlienReaderException e) {
				displayText("Parameter(s) not valid\n");
			}
		}	

		else if(cmd.equalsIgnoreCase("Clear")){
			//// Sets MessageTextArea to empty ////
			f.MessageTextArea.setText("");
		}

		else if(cmd.equalsIgnoreCase("Exit")){
			//// Close the connection then close the application ////
			ExecuteCommand("automode = off");
			mls.stopService();
			
			/*try {
				skt.close();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			reader.close();
			System.exit(0);
		}

		else if(cmd.equalsIgnoreCase("information")||cmd.equalsIgnoreCase("i")){
			try {
				displayText(reader.getInfo());
			} catch (AlienReaderException e) {
				e.printStackTrace();
			}
		}
		else if(cmd.equalsIgnoreCase("Discover")){
			if(parameters.equalsIgnoreCase("on")){
				try {
					ndls = new NetworkDiscoveryListenerService();
					ndls.setDiscoveryListener(this);
					ndls.startService();
					displayText("Discover = on\n");
				} catch (AlienDiscoverySocketException e) {
					e.printStackTrace();
				}
			}
			else if(parameters.equalsIgnoreCase("off")){
				ndls.stopService();
				displayText("Discover = off\n");
			}
		}
		else 
			displayText("Invalid command\n");
	}

	public void readerAdded(DiscoveryItem discoveryItem){
		displayText(discoveryItem.toTerseString() + "\n");
	}

	public void readerRenewed(DiscoveryItem discoveryItem){
	}

	public void readerRemoved(DiscoveryItem discoveryItem){
	}

	public void displayText(String text){
		f.MessageTextArea.append(text);		
	}

	public void setCommandAndParameters(String tempCommand){
		//variable that represents the index of the '=' in the string
		int equalsIndex = 0;

		//find the '='
		for(int i = 0; i < tempCommand.length(); i++){
			if(tempCommand.charAt(i) == 61)
				equalsIndex = i;
		}

		//if there is no '=' then the command takes no paramaters
		if(equalsIndex == 0){
			cmd = tempCommand.trim();
		}
		//otherwise, separate the command from the parameters
		else
		{
			cmd = tempCommand.substring(0, equalsIndex).trim();
			parameters = tempCommand.substring(equalsIndex + 1).trim();
		}

		//The command line is highlighted after it is entered 
		f.CmdTextField.selectAll();	
	}

	public void messageReceived(Message message){
		f.MessageTextArea.setText("");

		tagList = message.getTagList();

		if (message.getTagCount() == 0) {
			displayText("No Tags Found\n");
		} 
		else {
			displayText("Tag(s) found:\n");
			for (int i=0; i<message.getTagCount(); i++) {
				Tag tag = tagList[i];
				displayText("ID:" + tag.getTagID() +
						", Discovered:" + tag.getDiscoverTime() +
						", Last Seen:" + tag.getRenewTime() +
						", Antenna:" + tag.getAntenna() +
						", Reads:" + tag.getRenewCount() +"\n");
				//resets the number of times the tag was read.
				tag.setRenewCount(0);
			}
		}	
	}
}
