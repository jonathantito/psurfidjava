import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.logicalloy.ale.client.ECReportsListener;
import com.logicalloy.ale.client.TCPSubscriptionService;

import epcglobalAleXsd1.ECReportsDocument;

public class SampleClient implements ECReportsListener{

	SocketReaderFrame srf;

	public SampleClient(SocketReaderFrame srf) {
		this.srf = srf;
		//ALEClient client = new ALEClient("http://localhost:8080/api/services/ALEService");
		try {
			TCPSubscriptionService tcpss = new TCPSubscriptionService
					(Integer.parseInt(srf.portTextField.getText()),
					 InetAddress.getByName(srf.ipTextField.getText()));
			
			tcpss.addECReportsListener(this);
			tcpss.start();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*try {
			client.define("my ecspec", makeSpec());
			client.subscribe("my ecspec", "file:///TestReport5.xml");
			client.subscribe("my ecspec", "tcp://127.0.0.5:20004");		

			

		} catch (DuplicateNameExceptionResponse e) {
			e.printStackTrace();
		} catch (ECSpecValidationExceptionResponse e) {
			e.printStackTrace();
		} catch (ImplementationExceptionResponse e) {
			e.printStackTrace();
		} catch (SecurityExceptionResponse e) {
			e.printStackTrace();
		}  catch (DuplicateSubscriptionExceptionResponse e) {
			e.printStackTrace();
		} catch (InvalidURIExceptionResponse e) {
			e.printStackTrace();
		} catch (NoSuchNameExceptionResponse e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}*/
	}

	public void ecReportsReceived(ECReportsDocument doc){
		System.out.println(doc.toString());
	}

	/*private ECSpec makeSpec() {
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
	}*/
}
