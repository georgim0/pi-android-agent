package com.kupepia.piandroidagent;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	RelativeLayout mainRelativeLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainRelativeLayout = (RelativeLayout) this
				.findViewById(R.id.main_relative_layout);
		
		try {
			XML2Widget xml2Widget = new XML2Widget(this);
			//View view = xml2Widget.getView();
			ArrayList<Widget> wList = xml2Widget.getWidgets();
			
			for(Widget w : wList) {
				mainRelativeLayout.addView(w.createView(this));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
