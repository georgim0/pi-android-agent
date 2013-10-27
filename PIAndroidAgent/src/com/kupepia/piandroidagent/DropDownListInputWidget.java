package com.kupepia.piandroidagent;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DropDownListInputWidget extends InputWidget {

	private ArrayList<String> list = new ArrayList<String>();
	private Spinner spinner;
	public DropDownListInputWidget(String label, String uri) {
		super(label, uri);
	}

	
	
	@Override
	public View createMe(Context c) {
		spinner = new Spinner(c);		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,list);
		spinner.setAdapter(adapter);
		
		return spinner;
	}
	
	public void addEntry(String entry)
	{
		list.add(entry);
	}



	@Override
	public String getValue() {
		return this.spinner.getSelectedItem().toString();
	}

}
