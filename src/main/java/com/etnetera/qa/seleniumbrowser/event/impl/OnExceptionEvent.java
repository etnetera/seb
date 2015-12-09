package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class OnExceptionEvent extends BrowserEvent {

	protected Throwable throwable;

	public OnExceptionEvent with(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}
	
	@Override
	public void notify(BrowserListener listener) {
		listener.onException(this);
	}
	
	public Throwable getThrowable() {
		return throwable;
	}
	
}
