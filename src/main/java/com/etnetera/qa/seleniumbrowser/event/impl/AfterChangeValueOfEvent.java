package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class AfterChangeValueOfEvent extends WebElementEvent {
	
	@Override
	public void notify(BrowserListener listener) {
		listener.afterChangeValueOf(this);
	}
	
}
