package com.adi.lifeonterminal.adiserver.files;

import java.io.File;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

public class FileUploader {
	
	private DataInputStream dis;
	
	private static final int MIN_LIMIT = 0;
	private static final int MAX_LIMIT = 1024;
	
	public FileUploader(DataInputStream dis) {
		
		this.dis = dis;
	}
	
	public void upload() {
		
		try {
		
			String filename = "";
			String mime = "";
			
			while(true) {
				
				String line = dis.readLine().trim();
				if(line.length() == 0)
					break;
				if(line.contains("filename="))
					filename = line.split("filename=")[1].trim();
				if(line.contains("Content-Type:"))
					mime = line.split("Content-Type: ")[1].trim();		
			}
			
			filename = filename.substring(1, filename.length() - 1);
			
			
			File file = new File("res/hosted/"+filename);
			if(file.exists())
				file.delete();
			
			FileOutputStream fos = new FileOutputStream(file, true);
			
			int n = 0;
			byte[] data = new byte[MAX_LIMIT];
			while(dis.available() != 0) {
			
				n = dis.available() < MAX_LIMIT ? dis.available() : MAX_LIMIT;
			
				dis.readFully(data, MIN_LIMIT, n);
				fos.write(data, MIN_LIMIT, n);
			}
			
			fos.flush();
			fos.close();
			
		} catch(Exception e) {e.printStackTrace();}	
	}
}