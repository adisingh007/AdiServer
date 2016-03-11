package com.adi.lifeonterminal.adiserver.reader;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import com.adi.lifeonterminal.adiserver.files.HostedFile;
import com.adi.lifeonterminal.adiserver.client.AdiClientThread;

public class FilesLoader {
	
	private FilesLoader() {/* Class cannot be instantiated externally... */}
	
	public static HashMap<String, HostedFile> loadFiles(String hostedFileName) {
		
		HashMap<String, HostedFile> mapOfFiles = null;
		
		try {
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document doc = builder.parse(hostedFileName);
			mapOfFiles = loadFiles(doc);
		} catch(Exception e) {e.printStackTrace();}
		
		return mapOfFiles;
	}
	
	
	private static HashMap<String, HostedFile> loadFiles(Document doc) {
		
		NodeList listOfFiles = doc.getElementsByTagName("file");
		
		HashMap<String, HostedFile> mapOfFiles = new HashMap<>();
		
		for(int i = 0; i < listOfFiles.getLength(); i++) {
		
			Element fileElem = (Element)listOfFiles.item(i);
			String url      = ((Element)fileElem.getElementsByTagName("url").item(0)).getTextContent();
			String filename = ((Element)fileElem.getElementsByTagName("name").item(0)).getTextContent();
			String mime     = ((Element)fileElem.getElementsByTagName("mime").item(0)).getTextContent();
			
			HostedFile hostedFile = new HostedFile(filename, mime);
			
			mapOfFiles.put(url, hostedFile);
		}
		
		return mapOfFiles;
	}
}
