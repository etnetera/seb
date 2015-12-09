package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class BeforeScriptEvent extends ScriptEvent {
	
	@Override
	public void notify(BrowserListener listener) {
		listener.beforeScript(this);
	}
	
}
