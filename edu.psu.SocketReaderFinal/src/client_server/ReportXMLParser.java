package client_server;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ReportXMLParser {
    public ReportXMLParser() { 
    }
    
    public static String parse(InputStream is){
    	String result = "";

    	try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);

			// normalize text representation
			doc.getDocumentElement().normalize();

			NodeList listOfMembers = doc.getElementsByTagName("member");
			int totalMembers = listOfMembers.getLength();
	
			for(int s=0; s<listOfMembers.getLength() ; s++){
				Node epcMemberNode = listOfMembers.item(s);
				if(epcMemberNode.getNodeType() == Node.ELEMENT_NODE){

					Element epcMemberElement = (Element)epcMemberNode;

					//-------
					NodeList epcNameList = epcMemberElement.getElementsByTagName("epc");
					Element firstNameElement = (Element)epcNameList.item(0);

					NodeList textFNList = firstNameElement.getChildNodes();
					
					result = result + (s+1 + ")  " +
						((Node)textFNList.item(0)).getNodeValue().trim()) + "\n";
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
		return result;	
    }
}
