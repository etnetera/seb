package com.etnetera.qa.seleniumbrowser.event.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.page.Page;

abstract public class PageEvent extends BrowserEvent {

	protected Page page;

	public PageEvent with(Page page) {
		this.page = page;
		return this;
	}
	
	public Page getPage() {
		return page;
	}
	
}
