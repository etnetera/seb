package com.etnetera.qa.seleniumbrowser.listener.impl;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterPageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforePageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class ScreenshotListener extends BrowserListener {

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		super.init();
		// enable this listener on report and before browser quit as default
		enable(OnReportEvent.class, BeforeBrowserQuitEvent.class);
	}

	@Override
	public void beforePageInit(BeforePageInitEvent event) {
		takeScreenshot(event);
	}
	
	@Override
	public void afterPageInit(AfterPageInitEvent event) {
		takeScreenshot(event);
	}

	@Override
	public void beforeBrowserQuit(BeforeBrowserQuitEvent event) {
		takeScreenshot(event);
	}

	@Override
	public void onReport(OnReportEvent event) {
		takeScreenshot(event);
	}

	protected void takeScreenshot(BrowserEvent event) {
		if (event.getBrowser().isReported() && isScreenshotDriver(event))
			saveFile(event, event.getDriver(TakesScreenshot.class).getScreenshotAs(OutputType.BYTES), null, "png");
	}

	protected boolean isScreenshotDriver(BrowserEvent event) {
		return event.getDriver() instanceof TakesScreenshot;
	}

}
