package com.etnetera.qa.seleniumbrowser.listener.impl;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforePageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class ScreenshotListener extends BrowserListener {

	@Override
	public void beforePageInit(BeforePageInitEvent event) {
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
