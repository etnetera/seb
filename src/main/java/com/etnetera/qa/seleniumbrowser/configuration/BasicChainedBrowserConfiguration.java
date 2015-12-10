package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

/**
 * Basic implementation of {@link ChainedBrowserConfiguration}
 * which tries to get all possible values from chained configuration objects.
 */
public class BasicChainedBrowserConfiguration implements ChainedBrowserConfiguration, BrowserConfiguration {

	protected List<Object> configurations = new ArrayList<>();

	public BasicChainedBrowserConfiguration() {
	}

	/**
	 * Constructs a new instance and sets configurations.
	 */
	public BasicChainedBrowserConfiguration(Object... configurations) {
		if (configurations != null)
			this.configurations.addAll(Arrays.asList(configurations));
	}

	@Override
	public List<Object> getConfigurations() {
		return configurations;
	}

	@Override
	public String getBaseUrl() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getBaseUrl());
	}

	@Override
	public String getBaseUrlRegex() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getBaseUrlRegex());
	}

	@Override
	public Boolean isUrlVerification() {
		return getChainedValue(BrowserConfiguration.class, c -> c.isUrlVerification());
	}

	@Override
	public WebDriver getDriver() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getDriver());
	}

	@Override
	public Double getWaitTimeout() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getWaitTimeout());
	}

	@Override
	public Double getWaitRetryInterval() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getWaitRetryInterval());
	}

	@Override
	public Boolean isReported() {
		return getChainedValue(BrowserConfiguration.class, c -> c.isReported());
	}

	@Override
	public File getReportDir() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getReportDir());
	}

	@Override
	public List<BrowserListener> getListeners() {
		return getChainedListValue(BrowserConfiguration.class, BrowserListener.class, c -> c.getListeners());
	}

}
