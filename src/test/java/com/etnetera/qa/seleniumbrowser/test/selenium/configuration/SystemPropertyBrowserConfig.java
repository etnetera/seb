package com.etnetera.qa.seleniumbrowser.test.selenium.configuration;

import com.etnetera.qa.seleniumbrowser.configuration.BasicSystemPropertyBrowserConfiguration;

public class SystemPropertyBrowserConfig extends BasicSystemPropertyBrowserConfiguration implements BrowserConfig {

	protected String customPropertyPrefix = "browser.custom.";
	
	protected String username = "username";
	protected String password = "password";
	
	@Override
	public String getUsername() {
		return getProperty(customPropertyPrefix, username);
	}

	@Override
	public String getPassword() {
		return getProperty(customPropertyPrefix, password);
	}

}
