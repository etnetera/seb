package com.etnetera.qa.seleniumbrowser.test.selenium.configuration;

import com.etnetera.qa.seleniumbrowser.configuration.ChainedBrowserConfiguration;

public class ChainedBrowserConfig extends ChainedBrowserConfiguration implements BrowserConfig {

	@Override
	public String getUsername() {
		return getChainedValue(BrowserConfig.class, c -> c.getUsername());
	}

	@Override
	public String getPassword() {
		return getChainedValue(BrowserConfig.class, c -> c.getPassword());
	}

}
