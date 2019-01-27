package com.stockfaxforu.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import org.apache.xml.serialize.OutputFormat;
//import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.stockfaxforu.util.StockConstants;
//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
//import org.apache.xml.serialize.*;
public class ConfigXMLManager
{
	public static String ID = "id";
	public static String VALUE = "value";
	public static String TYPE = "type";
	public static String description = "description";
	public static String DEFAULT = "default";
	
	public static String PROPERTY = "property";

	public static Vector propertyVector=new Vector();
	public static HashMap propertyHash = new HashMap();
	public static HashMap categoryHash = new HashMap();
	
	
	public static void main(String[] args)
	{
		try
		{
			ConfigXMLManager smltesting = new ConfigXMLManager();
			smltesting.initialParser();
	//		smltesting.updateTag("Gambardella, Matthew", "mahesh kacholia");
			// (smltesting.propertyHash);
			// (smltesting.propertyVector);
			
	//		// ln("updated now prining again");
	//		// ln("updated now prining again");
			writingXML();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// ("finished");
	}

	public static void writingXML() throws Exception
	{
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
	//	StreamResult result = new StreamResult(new File("C:\\analysis\\file.xml"));
 

		// create string from xml tree
		 StringWriter sw = new StringWriter();
		 StreamResult result = new StreamResult(sw);
		// DOMSource source = new DOMSource(doc);
		 transformer.transform(source, result);
		 String xmlString = sw.toString();

		 File file = new File(StockConstants.propertyXML);
		 BufferedWriter bw = new BufferedWriter
		 (new OutputStreamWriter(new FileOutputStream(file)));
		 bw.write(xmlString);
		 bw.flush();
		 bw.close();


	}

	public static Document doc = null;

	public static void updateTag(String tagvalue, String value)
	{
			Element firstPersonElement = (Element) propertyHash.get(tagvalue);
			NodeList lastNameList = firstPersonElement.getElementsByTagName(VALUE);
			Element lastNameElement = (Element) lastNameList.item(0);
			NodeList textLNList = lastNameElement.getChildNodes();
			((Node) textLNList.item(0)).setNodeValue(value);

	}
	public static Element updateTag(Element firstPersonElement, String value)
	{
			NodeList lastNameList = firstPersonElement.getElementsByTagName(VALUE);
			Element lastNameElement = (Element) lastNameList.item(0);
			NodeList textLNList = lastNameElement.getChildNodes();
			((Node) textLNList.item(0)).setNodeValue(value);
			return 	firstPersonElement;
	}

	public static void initialParser()
	{
		try
		{

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(StockConstants.propertyXML));
			categoryHash  = new HashMap();
			propertyHash = new HashMap();
			propertyVector = new Vector();
	

			// normalize text representation
			doc.getDocumentElement().normalize();
		//	// ln("Root element of the doc is " + doc.getDocumentElement().getNodeName());

			NodeList listOfPersons = doc.getElementsByTagName(PROPERTY);
			int totalPersons = listOfPersons.getLength();
		//	// ln("Total no of people : " + totalPersons);

			for (int s = 0; s < listOfPersons.getLength(); s++)
			{

				Node firstPersonNode = listOfPersons.item(s);
				if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
				{

					Element firstPersonElement = (Element) firstPersonNode;
					String parent = firstPersonElement.getParentNode().getNodeName();
					Vector v = (Vector)categoryHash.get(parent);
					if(v==null)
					{
						v = new Vector();
						v.add(getDataForTag(firstPersonElement,ID));
						categoryHash.put(parent,v);
					}
					else
					{
						v.add(getDataForTag(firstPersonElement,ID));
					}
				//	// ln("parent="+parent);
					String id = getDataForTag(firstPersonElement,ID);
					propertyHash.put(id,firstPersonElement);
					propertyVector.add(id);
					
				//	// ln("id : " + getDataForTag(firstPersonElement,ID));
				//	// ln("value : " + getDataForTag(firstPersonElement,VALUE));
				//	// ln("First Name : " + getDataForTag(firstPersonElement,TYPE));
				//	// ln("First Name : " + getDataForTag(firstPersonElement,description));
				}
			}

		}
		catch (SAXParseException err)
		{
			// ln("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
		//	// ln(" " + err.getMessage());

		}
		catch (SAXException e)
		{
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		// System.exit (0);

	}// end of main
	
	public static Vector getAllIds()
	{
		return propertyVector;	
	}
	
	public static String getDataForTag(Element firstPersonElement,String id)
	{
		try
		{
			NodeList firstNameList = firstPersonElement.getElementsByTagName(id);
			Element firstNameElement = (Element) firstNameList.item(0);

			NodeList textFNList = firstNameElement.getChildNodes();
			return ((Node) textFNList.item(0)).getNodeValue().trim();
			
		}
		catch(Exception e)
		{
			return null;
		}
	
	}
}
