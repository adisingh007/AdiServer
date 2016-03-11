package com.adi.lifeonterminal.adiserver;

import com.adi.lifeonterminal.adiserver.server.AdiServer;
import com.adi.lifeonterminal.adiserver.server.AdiServerFactory;
import com.adi.lifeonterminal.adiserver.reader.ConfigurationManager;

public class AdiServerInit {
	
	public static void main(String[] args) {
		
		ConfigurationManager confMgr = new ConfigurationManager(args[0]);
		int port = confMgr.getServerPort();
		String hostsFile = confMgr.getHostsFile();
		
		AdiServer adiServer = AdiServerFactory.startServer(port, hostsFile);
	}
}
