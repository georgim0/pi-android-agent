package com.kupepia.piandroidagent;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;

public class XMLParser extends Activity {

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
