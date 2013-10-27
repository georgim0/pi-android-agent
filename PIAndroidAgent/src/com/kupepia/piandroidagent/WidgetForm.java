package com.kupepia.piandroidagent;

import java.util.ArrayList;

public class WidgetForm {
	
	private final ArrayList<Widget> widgets;
	private final SubmitButtonWidget submit;

	public WidgetForm(ArrayList<Widget> widgets, SubmitButtonWidget submit)
	{
		this.widgets = widgets;
		this.submit = submit;
	}

	public SubmitButtonWidget getSubmit() {
		return submit;
	}

	public ArrayList<Widget> getWidgets() {
		return widgets;
	}
}
