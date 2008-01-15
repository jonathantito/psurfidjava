import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.logicalloy.ale.client.ALEClient;
import com.logicalloy.ale.client.DuplicateNameExceptionResponse;
import com.logicalloy.ale.client.DuplicateSubscriptionExceptionResponse;
import com.logicalloy.ale.client.ECSpecValidationExceptionResponse;
import com.logicalloy.ale.client.ImplementationExceptionResponse;
import com.logicalloy.ale.client.InvalidURIExceptionResponse;
import com.logicalloy.ale.client.NoSuchNameExceptionResponse;
import com.logicalloy.ale.client.SecurityExceptionResponse;

import epcglobalAleXsd1.ECBoundarySpec;
import epcglobalAleXsd1.ECExcludePatterns;
import epcglobalAleXsd1.ECFilterSpec;
import epcglobalAleXsd1.ECLogicalReaders;
import epcglobalAleXsd1.ECReportOutputSpec;
import epcglobalAleXsd1.ECReportSetEnum;
import epcglobalAleXsd1.ECReportSetSpec;
import epcglobalAleXsd1.ECReportSpec;
import epcglobalAleXsd1.ECReportSpecs;
import epcglobalAleXsd1.ECSpec;
import epcglobalAleXsd1.ECSpecDocument;
import epcglobalAleXsd1.ECTime;
import epcglobalAleXsd1.ECTimeUnit;

public class SampleClient implements Runnable{

	ServerSocket srvr;
	Socket skt;
	BufferedReader in = null;

	public SampleClient() {
		ALEClient client = new ALEClient("http://localhost:8080/api/services/ALEService");

		try {
			client.define("my ecspec", makeSpec());
			client.subscribe("my ecspec", "file:///TestReport5.xml");
			client.subscribe("my ecspec", "tcp://127.0.0.5:20004");		

			Thread t = new Thread(this);			
			t.start();

		} catch (DuplicateNameExceptionResponse e) {
			e.printStackTrace();
		} catch (ImplementationExceptionResponse e) {
			e.printStackTrace();
		} catch (SecurityExceptionResponse e) {
			e.printStackTrace();
		} catch (ECSpecValidationExceptionResponse e) {
			e.printStackTrace();
		} catch (DuplicateSubscriptionExceptionResponse e) {
			e.printStackTrace();
		} catch (InvalidURIExceptionResponse e) {
			e.printStackTrace();
		} catch (NoSuchNameExceptionResponse e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new SampleClient();
	}

	public void run(){
		String line;
		String report;
		String parsedReport;
		boolean eof;

		while(true){
			eof = false;
			report = "";

			try{
				srvr = new ServerSocket();
				srvr.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.5"), 20004));
				skt = srvr.accept();
				in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
				//parsedReport = ReportXMLParser.parse(skt.getInputStream());
				
				while(!eof){
					line = in.readLine();
					if(line != null)
						report = report + line + "\n";
					else
						eof = true;

				}
				
				System.out.println(report);
				skt.close();
				srvr.close();

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
		}
	}

	private ECSpec makeSpec() {
		ECSpecDocument doc = ECSpecDocument.Factory.newInstance();

		ECSpec spec = doc.addNewECSpec();
		spec.setIncludeSpecInReports(true); // When sending reports, include this spec so the recipient knows what rules were used to build this report. This should be used for testing and really has no value for production.

		ECLogicalReaders readers = spec.addNewLogicalReaders();
		readers.addLogicalReader("IP");

		ECBoundarySpec boundarySpec = spec.addNewBoundarySpec();
		ECTime boundaryTime = ECTime.Factory.newInstance();
		boundaryTime.setUnit(ECTimeUnit.MS);
		boundaryTime.setLongValue(8000);
		boundarySpec.setDuration(boundaryTime); // Read tags for 30 seconds
		ECTime repeatPeriodTime = ECTime.Factory.newInstance();
		repeatPeriodTime.setUnit(ECTimeUnit.MS);
		repeatPeriodTime.setLongValue(100);
		boundarySpec.setRepeatPeriod(repeatPeriodTime); // Pause for 100 milliseconds between the 30-second cycles.
		ECTime stablePeriodTime = ECTime.Factory.newInstance();
		stablePeriodTime.setUnit(ECTimeUnit.MS);
		stablePeriodTime.setLongValue(8000);
		boundarySpec.setStableSetInterval(stablePeriodTime); // Pause for 100 milliseconds between the 30-second cycles.

		ECReportSpecs reports = spec.addNewReportSpecs();
		ECReportSpec report = reports.addNewReportSpec();
		report.setReportIfEmpty(true); // If not tags are read, don’t send this report.
		report.setReportName("Test Report");
		report.setReportOnlyOnChange(false); // If this report is exactly the same as the last report, don’t send it.

		ECFilterSpec filter = report.addNewFilterSpec();
		ECExcludePatterns exclPatterns = filter.addNewExcludePatterns();
		exclPatterns.addExcludePattern("urn:epc:pat:sgtin-96:*.*.*.*"); // We’re not interested in GID tags with serial numbers in the range of 5-100, exclude them from the report.

		ECReportOutputSpec output = report.addNewOutput();
		output.setIncludeCount(true);
		output.setIncludeEPC(true);
		output.setIncludeRawDecimal(true); // This method is deprecated and shouldn’t be used, but is included for sample purposes.
		output.setIncludeRawHex(true);
		output.setIncludeTag(true);

		ECReportSetSpec reportSet = report.addNewReportSet();
		reportSet.setSet(ECReportSetEnum.CURRENT); // Include ALL tags read during the event cycle.

		return spec;
	}
}
