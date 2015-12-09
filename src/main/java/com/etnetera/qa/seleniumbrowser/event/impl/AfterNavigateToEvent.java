package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class AfterNavigateToEvent extends UrlEvent {
	
	@Override
	public void notify(BrowserListener listener) {
		listener.afterNavigateTo(this);
	}
	
}
