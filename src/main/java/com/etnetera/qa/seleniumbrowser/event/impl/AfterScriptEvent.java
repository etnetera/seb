package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class AfterScriptEvent extends ScriptEvent {
	
	@Override
	public void notify(BrowserListener listener) {
		listener.afterScript(this);
	}
	
}
