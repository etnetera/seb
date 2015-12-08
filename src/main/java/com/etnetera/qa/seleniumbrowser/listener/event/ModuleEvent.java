package com.etnetera.qa.seleniumbrowser.listener.event;

import com.etnetera.qa.seleniumbrowser.module.Module;

abstract public class ModuleEvent extends BrowserEvent {

	protected Module module;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
}
