package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class AfterModuleInitEvent extends ModuleEvent {
	
	@Override
	public void notify(BrowserListener listener) {
		listener.afterModuleInit(this);
	}
	
}
