package com.etnetera.qa.seleniumbrowser.listener.event;

import com.etnetera.qa.seleniumbrowser.page.Page;

abstract public class PageEvent extends BrowserEvent {

	protected Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
