package com.kupepia.piandroidagent;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kupepia.piandroidagent.requests.RequestHandler;

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
			xml2Widget = new XML2Widget(RequestHandler.getDoc(this));
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
