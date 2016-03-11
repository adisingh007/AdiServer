package com.adi.lifeonterminal.adiserver.server;

public class AdiServerFactory {

	private static AdiServer adiServer = null;
	
	private AdiServerFactory() {/* Class cannot be instantiated externally... */}
	
	
	private static void init() {
		
		if(adiServer == null)
			adiServer = new AdiServer();
	}
	
	
	public static AdiServer startServer(int port) {
			
		init();	
			
		try {
			
			adiServer.startServer(port);
		} catch(Exception e) {e.printStackTrace();}	
		
		return adiServer;
	}
	
	public static AdiServer startServer(String hostsFileName) {
		
		init();
			
		try {
			
			adiServer.loadHostedFiles(hostsFileName);
		} catch(Exception e) {e.printStackTrace();}	
		
		return adiServer;
	}
	
	public static AdiServer startServer(int port, String hostsFileName) {
		
		init();
			
		try {
			
			adiServer.loadHostedFiles(hostsFileName);
			adiServer.startServer(port);
		} catch(Exception e) {e.printStackTrace();}	
		
		return adiServer;				
	}
}
