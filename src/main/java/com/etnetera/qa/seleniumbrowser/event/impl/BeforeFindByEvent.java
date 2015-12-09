package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class BeforeFindByEvent extends FindByEvent {

	@Override
	public void notify(BrowserListener listener) {
		listener.beforeFindBy(this);
	}
	
}
