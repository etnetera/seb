package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;

abstract public class UrlEvent extends BrowserEvent {

	protected String url;

	public UrlEvent with(String url) {
		this.url = url;
		return this;
	}
	
	public String getUrl() {
		return url;
	}
	
}
