package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class BeforeNavigateToEvent extends UrlEvent {
	
	@Override
	public void notify(BrowserListener listener) {
		listener.beforeNavigateTo(this);
	}
	
}
