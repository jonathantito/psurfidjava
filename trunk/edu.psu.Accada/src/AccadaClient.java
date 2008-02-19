import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.accada.epcis.captureclient.CaptureClient;


public class AccadaClient {
	private CaptureClient client = new CaptureClient();
	
	public AccadaClient(){
		
        try {
			InputStream fis = new FileInputStream("TestReport5.xml");
			int response = client.capture(fis);
			System.out.println(response);
			
			/*byte[] xml = new byte[fis.available()];
			fis.read(xml);

			String eventXml = new String(xml);
			System.out.println(eventXml);*/
			
			//client.
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
