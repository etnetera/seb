package com.etnetera.qa.seleniumbrowser.listener.impl;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterPageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforePageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class PageSourceListener extends BrowserListener {
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		super.init();
		// enable this listener on report and before browser quit as default
		enable(OnReportEvent.class, BeforeBrowserQuitEvent.class);
	}
	
	@Override
	public void beforePageInit(BeforePageInitEvent event) {
		savePageSource(event);
	}
	
	@Override
	public void afterPageInit(AfterPageInitEvent event) {
		savePageSource(event);
	}

	@Override
	public void beforeBrowserQuit(BeforeBrowserQuitEvent event) {
		savePageSource(event);
	}
	
	@Override
	public void onReport(OnReportEvent event) {
		savePageSource(event);
	}

	protected void savePageSource(BrowserEvent event) {
		if (event.getBrowser().isReported())
			saveFile(event, event.getDriver().getPageSource(), null, "html");
	}

}
