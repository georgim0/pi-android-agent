package com.kupepia.piandroidagent;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WidgetListAdapter extends BaseAdapter {

	private ArrayList<Widget> widgets = null;
	private Context mContext;
	
	public WidgetListAdapter(ArrayList<Widget> widgets, Context c)
	{
		this.widgets = widgets;
		this.mContext = c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return widgets.size();
	}

	@Override
	public Widget getItem(int arg0) {
		// TODO Auto-generated method stub
		return widgets.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return widgets.hashCode();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		try {
			arg1 = widgets.get(arg0).createView(this.mContext);
		}
		catch (NullPointerException npe)
		{
			arg1 = null;
		}
		if (arg1 == null)
		{
			TextView tv = new TextView(this.mContext);
			tv.setText("Class " + 
			widgets.get(arg0).getClass().getName() + " returns a null view");
			arg1 = tv;
			return arg1;
		}
		return arg1;
	}

}
