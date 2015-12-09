package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.module.Module;

abstract public class ModuleEvent extends BrowserEvent {

	protected Module module;

	public ModuleEvent with(Module module) {
		this.module = module;
		return this;
	}
	
	public Module getModule() {
		return module;
	}
	
}
