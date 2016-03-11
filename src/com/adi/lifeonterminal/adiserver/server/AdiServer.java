package com.adi.lifeonterminal.adiserver.server;

import java.util.HashMap;
import java.net.ServerSocket;

import com.adi.lifeonterminal.adiserver.files.HostedFile;
import com.adi.lifeonterminal.adiserver.reader.FilesLoader;
import com.adi.lifeonterminal.adiserver.client.AdiClientThread;

public class AdiServer {

	private ServerSocket servSock;
	private HashMap<String, HostedFile> mapOfFiles;

	/* Class cannot be instantiated from outside the package */
	AdiServer() {
	
		servSock = null;
	}
	
	void startServer(int port) throws Exception {
		
		if(servSock != null) {
		
			servSock.close();
			servSock = null;
		}	
		
		servSock = new ServerSocket(port);
		
		while(true) {
		
			Thread clientThread = new AdiClientThread(servSock.accept(), mapOfFiles);
			clientThread.start();
		}	
	}
	
	void loadHostedFiles(String hostedFileName) throws Exception {
		
		mapOfFiles = FilesLoader.loadFiles(hostedFileName);
	}
}
