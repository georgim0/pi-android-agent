package com.kupepia.piandroidagent;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DropDownListInputWidget extends InputWidget {

	public DropDownListInputWidget(String label, String uri) {
		super(label, uri);
	}

	@Override
	public View createMe(Context c) {
		Spinner spinner = new Spinner(c);
		ArrayList<String> list = new ArrayList<String>();
		list.add("no1");
		list.add("no2");		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,list);
		spinner.setAdapter(adapter);
		
		return spinner;
	}

}
