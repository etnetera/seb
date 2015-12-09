package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;

abstract public class ScriptEvent extends BrowserEvent {

	protected String script;

	public ScriptEvent with(String script) {
		this.script = script;
		return this;
	}
	
	public String getScript() {
		return script;
	}
	
}
