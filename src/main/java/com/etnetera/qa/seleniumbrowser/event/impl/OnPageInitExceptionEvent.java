package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;
import com.etnetera.qa.seleniumbrowser.page.Page;

public class OnPageInitExceptionEvent extends OnExceptionEvent {
	
	protected Page page;

	public OnPageInitExceptionEvent with(Page page, Throwable throwable) {
		this.page = page;
		super.with(throwable);
		return this;
	}
	
	@Override
	public void notify(BrowserListener listener) {
		listener.onException(this);
	}
	
	public Page getPage() {
		return page;
	}
	
}
