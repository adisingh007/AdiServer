package com.adi.lifeonterminal.adiserver.reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

public class ConfigurationManager {
	
	private static final String SERVER_PORT = "ServerPort";
	private static final String HOSTS_FILE  = "HostsFile";
	
	private Document doc;
	
	public ConfigurationManager(String configFile) {
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			doc = builder.parse("res/conf/"+configFile+".adiml");
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public int getServerPort() {
		
		Node serverPortNode = doc.getElementsByTagName(SERVER_PORT).item(0);
		Element serverPortElement= (Element)serverPortNode;
		
		int port = Integer.parseInt(serverPortElement.getTextContent().trim());
		
		return port;
	}
	
	
	public String getHostsFile() {
	
		Node hostsFileNode = doc.getElementsByTagName(HOSTS_FILE).item(0);
		Element hostsFileElement= (Element)hostsFileNode;
		
		String hostsFile = hostsFileElement.getTextContent().trim();
		
		return "res/conf/"+hostsFile+".adiml";
	}
}
