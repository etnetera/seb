package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class BeforeChangeValueOfEvent extends WebElementEvent {

	@Override
	public void notify(BrowserListener listener) {
		listener.beforeChangeValueOf(this);
	}
	
}
