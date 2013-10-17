package com.kupepia.piandroidagent;

import java.io.File;
import java.io.FileNotFoundException;

public class XMLParser {

	public XMLParser() throws FileNotFoundException{
		File x = new File("sample1.xml");
		
		if (!x.exists()){
			throw new FileNotFoundException();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		new XMLParser() ;
	}
}
