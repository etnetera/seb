package com.etnetera.qa.seleniumbrowser.listener.event;

abstract public class ScriptEvent extends BrowserEvent {

	protected String script;

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
}
