package com.etnetera.qa.seleniumbrowser.listener.event;

abstract public class UrlEvent extends BrowserEvent {

	protected String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
