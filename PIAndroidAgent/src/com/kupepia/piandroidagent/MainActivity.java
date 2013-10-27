package com.kupepia.piandroidagent;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import android.widget.Button;
import android.widget.ListAdapter;
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
	private ArrayList<MenuElement> menuList;
	private SubmitButtonWidget submitButton = null;
	private String address = "";
	private String apikey = "";
	private ArrayList<Widget> widgets;

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
		address = intent.getStringExtra("address");
		apikey = intent.getStringExtra("apikey");
		
		widgetsOnScreenListView = new ListView(this);
		mainRelativeLayout = (RelativeLayout)this.findViewById(R.id.main_relative_layout);
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
		if (item.getOrder() < this.menuList.size())
		{
			MenuElement menuElement = this.menuList.get(item.getOrder());
			new RequestView().execute(menuElement);
		}
		return super.onOptionsItemSelected(item);
	}
	
    private class RequestMenu extends AsyncTask<Menu, String, String> {

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
		protected String doInBackground(Menu... menus) {
			menu = menus[0];
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
    
    
    
    private class RequestView extends AsyncTask<MenuElement, String, String> {

		private String message = "";
		
		@Override
        protected void onPostExecute(String result) {
			if (this.isCancelled()) {
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				return;
			}
			
			ListAdapter adapter = new WidgetListAdapter(widgets, mContext);
			widgetsOnScreenListView.setAdapter(adapter);
	        mainRelativeLayout.addView(widgetsOnScreenListView);
	        if (submitButton != null)
	        {
	        	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
	        		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	        	lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
	        	lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        	Button button = submitButton.createView(mContext);
	        	button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						new SubmitRequestTask().execute(submitButton);
					}
				});
	        	mainRelativeLayout.addView(button, lp);
	        }
        }

        @Override
        protected void onPreExecute() {}

		@Override
		protected String doInBackground(MenuElement... menuElements) {
			MenuElement menuElement = menuElements[0];
			String url = menuElement.getUrl();
			String uri = menuElement.getUri();
			String value = menuElement.getValue();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(uri, value);
			
			try {	
				CommunicationManager cm = CommunicationManager.getInstance();
				Request request = RequestHandler.buildRequest(map);
				request.addAuthentication("admin", apikey);
				Document doc = cm.sendRequest(url, request);
				WidgetForm widgetForm = XML2Widget.XML2WidgetForm(doc);
				widgets = widgetForm.getWidgets();
				submitButton = widgetForm.getSubmit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				this.cancel(true);
				this.message  = e.toString();
				e.printStackTrace();
			}
			
			
			return null;
		}
    	
    }

    private class SubmitRequestTask extends AsyncTask<SubmitButtonWidget, String, String> {
    	Document doc = null;
    	private String message = "";
		
		@Override
        protected void onPostExecute(String result) {
			if (this.isCancelled()) {
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				return;
			}
			
			ListAdapter adapter = new WidgetListAdapter(widgets, mContext);
			widgetsOnScreenListView.setAdapter(adapter);
	        mainRelativeLayout.addView(widgetsOnScreenListView);
	        if (submitButton != null)
	        {
	        	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
	        		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	        	lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
	        	lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        	Button button = submitButton.createView(mContext);
	        	button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						new SubmitRequestTask().execute(submitButton);
					}
				});
	        	mainRelativeLayout.addView(button, lp);
	        }
        }
		
		@Override
		protected String doInBackground(SubmitButtonWidget... sbw) {
			
			SubmitButtonWidget sButton = sbw[0];
			Request request = null;
			try {
				request = Request.createFormatRequest(sButton.getRequestFormat().getUrl());
				request.addAuthentication("admin", apikey);
			} catch (ParserConfigurationException e) {
				this.cancel(true);
			}	
			CommunicationManager cm = CommunicationManager.getInstance();
			if (request != null)
				try {
					Document formatDoc = cm.sendRequest("/api-bin/request_fetcher.py", request);
					String xmlDoc = XMLUtils.Document2String(formatDoc);
					HashMap<String, String> map = new HashMap<String, String>();
					for (Widget w : widgets)
					{
						if (w instanceof InputWidget)
						{
							String uri = ((InputWidget)w).getUri();
							String value = ((InputWidget)w).getValue();
							map.put(uri, value);
							
						}
					}
					
					Request submitRequest = RequestHandler.buildRequest(formatDoc, map);
					submitRequest.addAuthentication("admin", apikey);
					String submitURL = sButton.getUrl();
					cm.sendRequest(submitURL, submitRequest);
				} catch (Exception e) {
					this.cancel(true);
				}//catch
			else
				return null;
			if (doc == null) 
				return null;
			
			
			return null;
		}
    
    }
    	
}
