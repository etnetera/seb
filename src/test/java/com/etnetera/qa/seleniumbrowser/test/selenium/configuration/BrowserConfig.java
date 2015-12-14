package com.etnetera.qa.seleniumbrowser.test.selenium.configuration;

import com.etnetera.qa.seleniumbrowser.configuration.BasicBrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.source.PropertiesSource;

public class BrowserConfig extends BasicBrowserConfiguration {

	public static final String PREFIX = "browser.custom.";

	public static final String USERNAME = PREFIX + "username";
	public static final String PASSWORD = PREFIX + "password";

	@Override
	public void init() {
		super.init();
		addResourcePropertiesAfter(BasicBrowserConfiguration.SYSTEM_PROPERTIES_KEY, "custom",
				"customProperties.properties");
		addData("custom", PropertiesSource.loadProperties("customData.properties"));
	}

	@Override
	protected String getDefaultBaseUrl() {
		return "http://www.etnetera.cz";
	}

	@Override
	protected boolean isDefaultReported() {
		return true;
	}

	public String getUsername() {
		return getProperty(USERNAME);
	}

	public String getPassword() {
		return getProperty(PASSWORD);
	}

}
