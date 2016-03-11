package com.adi.lifeonterminal.adiserver.files;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class HostedFile {
	
	private String filename;
	private String mime;
	
	private byte[] data;
	
	public HostedFile(String filename, String mime) {
		
		this.filename = filename;
		this.mime = mime;
		
		String filePath = "res/hosted/"+filename;
		
		try {
		
			Path path = Paths.get(filePath);
			data = Files.readAllBytes(path);
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public String getFilename() {
	
		return filename;
	}
	
	public String getMime() {
		
		return mime;
	}
	
	public byte[] getData() {
		
		return data;
	}
}
