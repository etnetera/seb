package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;
import com.etnetera.qa.seleniumbrowser.module.Module;

public class OnModuleInitExceptionEvent extends OnExceptionEvent {
	
	protected Module module;

	public OnModuleInitExceptionEvent with(Module module, Throwable throwable) {
		this.module = module;
		super.with(throwable);
		return this;
	}
	
	@Override
	public void notify(BrowserListener listener) {
		listener.onException(this);
	}
	
	public Module getModule() {
		return module;
	}
	
}
