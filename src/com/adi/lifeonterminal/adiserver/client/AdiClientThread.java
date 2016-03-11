package com.adi.lifeonterminal.adiserver.client;

import java.io.InputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.HashMap;

import com.adi.lifeonterminal.adiserver.files.HostedFile;

public class AdiClientThread extends Thread {
	
	private Socket socket;
	private HashMap<String, HostedFile> listOfFiles;
	
	public AdiClientThread(Socket socket, HashMap<String, HostedFile> listOfFiles) {
		
		this.socket = socket;
		this.listOfFiles = listOfFiles;
	}
	
	@Override
	public void run() {
	
		try {
			
			Scanner sc = new Scanner(socket.getInputStream());
			String headerLine = sc.nextLine().trim();
			
			String[] headerParts = headerLine.split(" ");
			String fileName = headerParts[1].substring(1, headerParts[1].length());
			
			// Code here for upload...
			
			HostedFile hostedFile = listOfFiles.get(fileName);
			String errorCode = "200 OK";
			if(hostedFile == null) {
			
				hostedFile = listOfFiles.get("404NotFound");
				errorCode = "404 Not Found";
			}	
						
			byte[] data = hostedFile.getData();
			
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());	
			dos.writeBytes("HTTP/1.1 "+errorCode+"\r\n");
			dos.writeBytes("Server: AdiServer\r\n");
			dos.writeBytes("Content-Type: "+hostedFile.getMime()+"\r\n");
			dos.writeBytes("Content-Length: "+data.length+"\r\n");
			dos.writeBytes("\r\n");
			dos.write(data, 0, data.length);
			
			dos.flush();
			
			sc.close();
			dos.close();
			socket.close();
		} catch(Exception e) {e.printStackTrace();}
	}
}
