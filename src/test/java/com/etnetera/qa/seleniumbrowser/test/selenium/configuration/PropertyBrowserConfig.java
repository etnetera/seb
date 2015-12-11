package com.etnetera.qa.seleniumbrowser.test.selenium.configuration;

import com.etnetera.qa.seleniumbrowser.configuration.PropertyBrowserConfiguration;

public interface PropertyBrowserConfig extends PropertyBrowserConfiguration, BrowserConfig {

	public static final String PREFIX = "browser.custom.";
	
	public static final String USERNAME = PREFIX + "username";
	public static final String PASSWORD = PREFIX + "password";
	
	@Override
	public default String getUsername() {
		return getProperty(USERNAME);
	}

	@Override
	public default String getPassword() {
		return getProperty(PASSWORD);
	}

}
