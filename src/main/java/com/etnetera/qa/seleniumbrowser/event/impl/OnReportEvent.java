package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class OnReportEvent extends BrowserEvent {

	protected String label;

	public OnReportEvent with(String label) {
		this.label = label;
		return this;
	}
	
	@Override
	public void notify(BrowserListener listener) {
		listener.onReport(this);
	}
	
	public String getLabel() {
		return label;
	}
	
}
