package com.etnetera.qa.seleniumbrowser.test.selenium.configuration;

import java.util.Arrays;
import java.util.List;

import com.etnetera.qa.seleniumbrowser.configuration.DefaultBrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;
import com.etnetera.qa.seleniumbrowser.listener.impl.PageSourceListener;
import com.etnetera.qa.seleniumbrowser.listener.impl.ScreenshotListener;

public class DefaultBrowserConfig extends DefaultBrowserConfiguration implements BrowserConfig {

	@Override
	public String getBaseUrl() {
		return "http://www.etnetera.cz";
	}

	@Override
	public String getUsername() {
		return "user";
	}

	@Override
	public String getPassword() {
		return "pass";
	}

	@Override
	public Boolean isReported() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BrowserListener> getListeners() {
		return Arrays.asList(new PageSourceListener().disable(OnReportEvent.class), new ScreenshotListener());
	}

}
