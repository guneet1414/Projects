package com.notepad.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileIO {
	
	public String openFile(File file) {
		
		String content = "";
		try {
			//reading data from file to string object
			content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(e.getMessage());
		}
		return content;
	}
	
	public void saveFile(File file, String extension, String content) {
		String path = file.getAbsolutePath() + extension;
		
		try {
			//writing data to file
			Files.write(Paths.get(path), content.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(e.getMessage());
		}
		
	}

}
