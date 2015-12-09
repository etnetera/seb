package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class BeforePageInitEvent extends PageEvent {
	
	@Override
	public void notify(BrowserListener listener) {
		listener.beforePageInit(this);
	}
	
}
