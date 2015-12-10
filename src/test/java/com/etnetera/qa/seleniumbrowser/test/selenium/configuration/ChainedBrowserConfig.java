package com.etnetera.qa.seleniumbrowser.test.selenium.configuration;

import com.etnetera.qa.seleniumbrowser.configuration.BasicChainedBrowserConfiguration;

public class ChainedBrowserConfig extends BasicChainedBrowserConfiguration implements BrowserConfig {

	public ChainedBrowserConfig() {
		super();
	}

	public ChainedBrowserConfig(Object... configurations) {
		super(configurations);
	}

	@Override
	public String getUsername() {
		return getChainedValue(BrowserConfig.class, c -> c.getUsername());
	}

	@Override
	public String getPassword() {
		return getChainedValue(BrowserConfig.class, c -> c.getPassword());
	}

}
