package com.etnetera.qa.seleniumbrowser.listener.event;

public class OnExceptionEvent extends BrowserEvent {

	protected Throwable throwable;

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	
}
