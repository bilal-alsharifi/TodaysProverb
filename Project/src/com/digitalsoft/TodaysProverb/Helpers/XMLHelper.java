package com.digitalsoft.TodaysProverb.Helpers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.digitalsoft.TodaysProverb.Entities.Package;


public class XMLHelper 
{
	public static String getXmlFromUrl(String url) throws IOException 
	{  
		String result = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        result = EntityUtils.toString(httpEntity, "UTF-8");
        return result; 
    }
	public static Document getDomElement(String xml) throws SAXException
	{
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try 
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is); 
        } 
        catch (ParserConfigurationException e) 
        {
        	e.printStackTrace();
        	return null;
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
        	return null;
        }
        return doc;
    }
	public static ArrayList<String> getElementsByTagName(String url, String tag) throws IOException, SAXException
	{
		ArrayList<String> result = new ArrayList<String>();
		String xml = XMLHelper.getXmlFromUrl(url);
	    Document doc = XMLHelper.getDomElement(xml);
	    if(doc != null)
	    {
	    	NodeList nl = doc.getElementsByTagName(tag);
	    	String value;
	    	for (int i = 0; i < nl.getLength(); i++) 
	    	{
	    		value = nl.item(i).getFirstChild().getNodeValue();
	    		result.add(value);
	    	}
	    }
	    return result;
	}
	public static ArrayList<Package> getPackages(String url) throws IOException, SAXException
	{
		ArrayList<Package> result = new ArrayList<Package>();
		String xml = XMLHelper.getXmlFromUrl(url);
	    Document doc = XMLHelper.getDomElement(xml);
	    if(doc != null)
	    {
	    	NodeList nl = doc.getElementsByTagName("package");
	    	String tableName;
	    	String packageName;
	    	String category;
	    	for (int i = 0; i < nl.getLength(); i++) 
	    	{
	    		packageName = nl.item(i).getFirstChild().getNodeValue();
	    		tableName = nl.item(i).getAttributes().getNamedItem("table").getNodeValue();
	    		category = nl.item(i).getAttributes().getNamedItem("category").getNodeValue();
	    		result.add(new Package(tableName, packageName, category));
	    	}
	    }
	    return result;
	}
	public static ArrayList<String> getPackagesNames(ArrayList<Package> packages)
	{	
	    ArrayList<String> result = new ArrayList<String>();
	    for(Package pkg:packages)
	    {
	    	result.add(pkg.getPackageName());
	    }
		return result;
	}
	public static String getTableName(ArrayList<Package> packages, String packageName)
	{	
		for(Package pkg:packages)
        {
			if (pkg.getPackageName().equals(packageName))
			{
				return pkg.getTableName();
			}
        }
		return null;
	}
	public static String getCategory(ArrayList<Package> packages, String packageName)
	{	
		for(Package pkg:packages)
        {
			if (pkg.getPackageName().equals(packageName))
			{
				return pkg.getCategory();
			}
        }
		return null;
	}
}
