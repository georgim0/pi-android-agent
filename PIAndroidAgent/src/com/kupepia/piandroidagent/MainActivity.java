package com.kupepia.piandroidagent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Request;
import com.kupepia.piandroidagent.requests.RequestHandler;
import com.kupepia.piandroidagent.utils.XMLUtils;

public class MainActivity extends Activity {
	private RelativeLayout mainRelativeLayout = null;
	private ListView widgetsOnScreenListView = null;
	private Context mContext;
	String username = "";
	String apikey = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainRelativeLayout = (RelativeLayout) this
				.findViewById(R.id.main_relative_layout);
		CommunicationManager cm = CommunicationManager.getInstance();
		cm.setContext(this);
		Intent intent = this.getIntent();
		String id = intent.getStringExtra("username");
		String name = intent.getStringExtra("apikey");
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
	
	private void generateView(Document doc)
	{
		mainRelativeLayout.removeAllViews();
		widgetsOnScreenListView = new ListView(this);
		XML2Widget xml2Widget = null;
		try {
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
	}//generate view
	
    private class RequestFromMenu extends AsyncTask<MenuElement, String, String> {

		
		@Override
        protected void onPostExecute(String result) {
           
        }

        @Override
        protected void onPreExecute() {}

		@Override
		protected String doInBackground(MenuElement... menuElement) {
			MenuElement me = menuElement[0];
			String url = me.getUrl();
			String uri = me.getUri();
			String value = me.getValue();
			InputStream is = null;
			try {	
				HashMap<String, String> map = new HashMap<String, String>();
				Request req = RequestHandler.buildRequest(map);
				map.put(uri, value);
				CommunicationManager cm = CommunicationManager.getInstance();
				req.addAuthentication(username, apikey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}
    	
    }
}
