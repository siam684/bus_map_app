package com.hoh.nycbusschedules;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import javax.xml.bind.JAXBException;

public class DBQueryList {

	private static final String URI = "http://ec2-54-148-190-68.us-west-2.compute.amazonaws.com:8080/Search/rest/search/";
	HttpURLConnection connection;
	InputStream returnStream;
	
	
	public DBQueryList getFirstLetterRoutes() throws IOException
	{
		
		//create string of GET URL
    	URL url = new URL(URI +  "getFirstLetterRoutes");
        connectAndRequest(url);
        return this;
    }
	
	public DBQueryList getDefinedListRoutes(String letter) throws IOException
	{
		
		//create string of GET URL
    	URL url = new URL(URI +  "getDefinedListRoutes/"+letter.toUpperCase());
        connectAndRequest(url);
        return this;
    }
	
	public DBQueryList getDirection(String routeId) throws IOException 
	{
		//create string of GET URL
    	URL url = new URL(URI +  "getDirection/"+ routeId.toUpperCase());
         connectAndRequest(url);
         return this;
	}
	
	public DBQueryList getStopList(String routeId, String direction) throws IOException 
	{
		//create string of GET URL
		URL url =  new URL(URI + "getStopListD" + direction + "/" + routeId.toUpperCase() + getDayOfWeek() );		
        connectAndRequest(url);
        return this;
	}
	
	
	public DBQueryList getArrivalTimes(String routeId, String direction, String stopId) throws IOException 
	{
		//create string of GET URL
    	URL url = new URL(URI +  "getArrivalTimes/"+ routeId.toUpperCase() + getDayOfWeek() + "/" + direction + "/" + stopId);
        connectAndRequest(url);
        return this;
	}
	
	private String getDayOfWeek()
	{
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		String dayOfWeek = null;
		
		if(day==1)
		{
			dayOfWeek = "/Sunday";
		}
		else if(day==7)
		{
			dayOfWeek = "/Saturday";
		}
		else
		{
			dayOfWeek = "/Weekday";
		}
		
		return dayOfWeek;
	}
	
	
	
	private  void connectAndRequest(URL url) throws IOException
	{
		//open connection
        connection = (HttpURLConnection) url.openConnection();
       
        //set type of url request
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        //send request and get reply in xml format
        returnStream = connection.getInputStream();
	}
	
	public String toString()
	{
		 ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		 int nRead;
		 byte[] data = new byte[16384];

		 try 
		 {
			while ((nRead = returnStream.read(data, 0, data.length)) != -1) 
			{
			   buffer.write(data, 0, nRead);
			}
			buffer.flush();
		 } 
		 catch (IOException e) 
		 {
			e.printStackTrace();
		 }
		 connection.disconnect();
		 return  buffer.toString();
	}
	
	public ArrayList<String> toStringList()
	{
		ArrayList<String> stringList = new ArrayList<String>();
		try 
		{
				
			 	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();	
			
			 	//Using factory get an instance of document builder
				DocumentBuilder db = dbf.newDocumentBuilder();
				//parse using bufaceilder to get DOM representation of the XML file
				Document dom = db.parse(returnStream);
				Element docEle = dom.getDocumentElement();
				NodeList nl = docEle.getElementsByTagName("Item");
				
				if(nl != null && nl.getLength() > 0) 
				{
					for(int i = 0 ; i < nl.getLength();i++) 
					{
						stringList.add(nl.item(i).getTextContent());
					}
				}
			
				connection.disconnect();
		}
		catch(ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		}
		catch(SAXException se) 
		{
			se.printStackTrace();
		}
		catch(IOException ioe) 
		{
			ioe.printStackTrace();
		}
		
		return stringList;
	}
	
}
