package com.adi.lifeonterminal.adiserver.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.HashMap;

import com.adi.lifeonterminal.adiserver.files.HostedFile;
import com.adi.lifeonterminal.adiserver.files.FileUploader;

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
			
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String headerLine = dis.readLine().trim();
			
			String[] headerParts = headerLine.split(" ");
			String fileName = headerParts[1].substring(1, headerParts[1].length());
			
			if(fileName.equals("adi_server_upload")) {
			
				int count = 0;
				while(true) {
					
					String line = dis.readLine().trim();
					if(line.length() == 0)
						count++;
					if(count == 1)
						break;	
				}
			
				FileUploader fup = new FileUploader(dis);
				fup.upload();
			}
			
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
			dos.close();
			socket.close();
		} catch(Exception e) {e.printStackTrace();}
	}
}
