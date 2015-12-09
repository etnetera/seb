package com.etnetera.qa.seleniumbrowser.listener.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforePageInitEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class PageSourceListener extends BrowserListener {

	@Override
	public void beforePageInit(BeforePageInitEvent event) {
		savePageSource(event);
	}
	
	protected void savePageSource(BrowserEvent event) {
		if (event.getBrowser().isReported())
			saveFile(event, event.getDriver().getPageSource(), null, "html");
	}

}
