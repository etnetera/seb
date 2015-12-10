package com.etnetera.qa.seleniumbrowser.test.selenium.configuration;

import com.etnetera.qa.seleniumbrowser.configuration.BasicDefaultBrowserConfiguration;

public class DefaultBrowserConfig extends BasicDefaultBrowserConfiguration implements BrowserConfig {

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

}
