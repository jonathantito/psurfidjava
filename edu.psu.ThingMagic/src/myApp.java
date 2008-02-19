import MercuryEJavaAPI.MercuryEApplication;
import MercuryEJavaAPI.ReaderDevice;
import javax.comm.*;

public class myApp implements MercuryEApplication{
	ReaderDevice myReader;
	ReaderDevice.TagId t;
	short region = 1;
	short rxA = 1;
	short txA = 1;
	long port = 0;
	ReaderDevice.AntennaPort ap;
	boolean on;
	
	public myApp(){
		
	}
	
	public void createReader(){
		int commPort = 1;
		int baudRate = 9600;
		
		try {
			myReader = new ReaderDevice();
			ap = new ReaderDevice.AntennaPort();
			ap.rxAntenna = rxA;
			ap.txAntenna = txA;
		//	myReader.simulatedSerialConnectTo(this, commPort, baudRate);
			SerialPort sp = SerialPort.;
			baudRate = sp.getBaudRate();
			myReader.serialConnectTo(this, commPort, baudRate);
			myReader.setTagProtocol(ReaderDevice.PROTOCOL_GEN2);
			myReader.setRegion(ReaderDevice.REGION_NA);
			myReader.setAntennaPort(ap);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}		
	}
	
	public void readTag(){
		t = myReader.readTagIdSingle(100);
		//myReader.readTagIdMultiple(1000);
	}
	
	public void messageStringConsume(MessageType m , String s){
		
	}
	
	public void readerErrorMessageConsume(short x, String s, int y, int z){
		
	}
}
