package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class AfterNavigateBackEvent extends BrowserEvent {

	@Override
	public void notify(BrowserListener listener) {
		listener.afterNavigateBack(this);
	}
	
}