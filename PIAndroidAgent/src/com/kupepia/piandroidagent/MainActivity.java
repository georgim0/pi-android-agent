package com.kupepia.piandroidagent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
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
	String address = "";
	String apikey = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainRelativeLayout = (RelativeLayout) this
				.findViewById(R.id.main_relative_layout);
		mContext = this;
		CommunicationManager cm = CommunicationManager.getInstance();
		cm.setContext(this);
		Intent intent = this.getIntent();
		/*address = intent.getStringExtra("address");
		apikey = intent.getStringExtra("apikey");*/
		address = "https://192.168.56.101:8005";
		apikey = "27WS0aRgGyf1c";
	}

	private void showMsg(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		new RequestMenu().execute(menu);
		

		return true;
	}

	private ArrayList<MenuElement> getMenuList() throws Exception {

		Document doc = null;
		CommunicationManager cm = CommunicationManager.getInstance();
		cm.setContext(mContext);
		cm.setRemoteHost(address);
		Request req = RequestHandler.buildRequest(new HashMap<String, String>());
		req.addAuthentication("admin", apikey);
		doc = cm.sendRequest("/api-bin/action_menu.py", req);
			
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
	
    private class RequestMenu extends AsyncTask<Menu, String, String> {

    	private ArrayList<MenuElement> menuList;
    	private Menu menu;
		private String message = "";
		@Override
        protected void onPostExecute(String result) {
			if (this.isCancelled()) {
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				return;
			}
           for (MenuElement me : menuList)
           {
        	   menu.add(me.getLabel());
           }
        }

        @Override
        protected void onPreExecute() {}

		@Override
		protected String doInBackground(Menu... menus) {
			menu = menus[0];
			InputStream is = null;
			try {	
				 menuList = getMenuList();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				this.cancel(true);
				this.message  = e.toString();
				e.printStackTrace();
			}
			
			
			return null;
		}
    	
    }
}
