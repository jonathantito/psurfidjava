
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class xml{

	public static void main (String argv []){
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File("C:/TestReport1.xml"));

			// normalize text representation
			doc.getDocumentElement().normalize();
			System.out.println ("Root element of the doc is " + 
					doc.getDocumentElement().getNodeName());

			NodeList listOfMembers = doc.getElementsByTagName("member");
			int totalMembers = listOfMembers.getLength();
			System.out.println("Total no of members : " + totalMembers);

			for(int s=0; s<listOfMembers.getLength() ; s++){


				Node epcMemberNode = listOfMembers.item(s);
				if(epcMemberNode.getNodeType() == Node.ELEMENT_NODE){

					Element epcMemberElement = (Element)epcMemberNode;

					//-------
					NodeList epcNameList = epcMemberElement.getElementsByTagName("epc");
					Element firstNameElement = (Element)epcNameList.item(0);

					NodeList textFNList = firstNameElement.getChildNodes();
					System.out.println(s+1 + ")  " +
							((Node)textFNList.item(0)).getNodeValue().trim());
				}
			}

		}catch (SAXParseException err) {
			System.out.println ("** Parsing error" + ", line " 
					+ err.getLineNumber () + ", uri " + err.getSystemId ());
			System.out.println(" " + err.getMessage ());

		}catch (SAXException e) {
			Exception x = e.getException ();
			((x == null) ? e : x).printStackTrace ();

		}catch (Throwable t) {
			t.printStackTrace ();
		}
		//System.exit (0);

	}//end of main


}