package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

/**
 * Basic implementation of {@link BrowserConfiguration}
 * with directly defined values.
 */
public class DefaultBrowserConfiguration implements BrowserConfiguration {
	
	@Override
	public String getBaseUrl() {
		return null;
	}

	@Override
	public String getBaseUrlRegex() {
		String baseUrl = getBaseUrl();
		return baseUrl == null ? null : Pattern.quote(baseUrl);
	}
	
	@Override
	public Boolean isUrlVerification() {
		return true;
	}
	
	@Override
	public WebDriver getDriver(DesiredCapabilities caps) {
		return new FirefoxDriver(caps);
	}
	
	@Override
	public DesiredCapabilities getCapabilities() {
		return null;
	}
	
	@Override
	public Double getWaitTimeout() {
		return 5d;
	}
	
	@Override
	public Double getWaitRetryInterval() {
		return 0.1;
	}
	
	@Override
	public Boolean isReported() {
		return false;
	}
	
	@Override
	public File getReportDir() {
		return new File("selenium-browser-report");
	}
	
	@Override
	public List<BrowserListener> getListeners() {
		return null;
	}
	
}
