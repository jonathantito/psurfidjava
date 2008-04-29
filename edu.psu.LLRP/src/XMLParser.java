import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLParser {
    public XMLParser() { 
    }
    
    public static String parse(String xmlstring){
    	String result = "";

    	try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource instream = new InputSource();
			instream.setCharacterStream(new StringReader(xmlstring));
			Document doc = docBuilder.parse(instream);

			// normalize text representation
			doc.getDocumentElement().normalize();

			NodeList listOfMembers = doc.getElementsByTagName("EPC96");
			
			int totalMembers = listOfMembers.getLength();

			for(int s=0; s<listOfMembers.getLength() ; s++){
				Node epcMemberNode = listOfMembers.item(s);
				if(epcMemberNode.getNodeType() == Node.ELEMENT_NODE){

					Element epcMemberElement = (Element)epcMemberNode;

					//-------
					NodeList epcNameList = epcMemberElement.getElementsByTagName("EPC");
					result = result + (s+1 + ")  " + epcNameList.item(0).getAttributes().item(0).getNodeValue() + "\n");
							//((Node)epcNameList.item(0)).getNodeValue().trim()) + "\n";
					
					//Element firstNameElement = (Element)epcNameList.item(0);

					//NodeList textFNList = firstNameElement.getChildNodes();
					
					//result = result + (s+1 + ")  " +
						//((Node)textFNList.item(0)).getNodeValue().trim()) + "\n";
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
