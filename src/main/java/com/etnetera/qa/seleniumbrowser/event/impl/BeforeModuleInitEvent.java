package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class BeforeModuleInitEvent extends ModuleEvent {
	
	@Override
	public void notify(BrowserListener listener) {
		listener.beforeModuleInit(this);
	}
	
}
