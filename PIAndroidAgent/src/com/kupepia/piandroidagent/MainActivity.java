package com.kupepia.piandroidagent;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kupepia.piandroidagent.utils.XMLUtils;

public class MainActivity extends Activity {
	RelativeLayout mainRelativeLayout = null;
	ListView widgetsOnScreenListView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainRelativeLayout = (RelativeLayout) this
				.findViewById(R.id.main_relative_layout);
		widgetsOnScreenListView = new ListView(this);
		XML2Widget xml2Widget = null;
		try {
			Document doc = XMLUtils.stream2Document(
					this.getResources().openRawResource(R.raw.sample1));
			xml2Widget = new XML2Widget(doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// View view = xml2Widget.getView();
		if (xml2Widget != null) {
			ArrayList<Widget> wList = xml2Widget.getWidgets();
			WidgetListAdapter wla = new WidgetListAdapter(wList, this);
			this.widgetsOnScreenListView.setAdapter(wla);
			mainRelativeLayout.addView(this.widgetsOnScreenListView);
		
			
			

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
