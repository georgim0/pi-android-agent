package com.kupepia.piandroidagent;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kupepia.piandroidagent.requests.RequestHandler;
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
			Document doc = XMLUtils.stream2Document(this.getResources()
					.openRawResource(R.raw.sample1));
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

	private void showMsg(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);

		ArrayList<MenuElement> menuList = getMenuList();
		for (MenuElement menuElement : menuList) {
			menu.add(menuElement.getLabel());
		}

		return true;
	}

	private ArrayList<MenuElement> getMenuList() {

		Document doc = null;
		try {
			doc = XMLUtils.stream2Document(this.getResources().openRawResource(
					R.raw.sample_menu));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XML2Menu.getInstance(doc);
		return XML2Menu.getInstance(null).getMenuList();

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection

		String label = item.getTitle().toString();
		MenuElement selectedMenuElement = XML2Menu.getInstance(null)
				.getSelected(label);

		showMsg("label: " + selectedMenuElement.getLabel() + ", uri: "
				+ selectedMenuElement.getUri() + ", value: "
				+ selectedMenuElement.getValue());
		return super.onOptionsItemSelected(item);
	}
}
