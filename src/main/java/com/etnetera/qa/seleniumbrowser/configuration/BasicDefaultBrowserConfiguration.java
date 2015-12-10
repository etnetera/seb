package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;
import com.etnetera.qa.seleniumbrowser.listener.impl.PageSourceListener;
import com.etnetera.qa.seleniumbrowser.listener.impl.ScreenshotListener;

/**
 * Basic implementation of {@link BrowserConfiguration}
 * with values directly defined.
 */
abstract public class BasicDefaultBrowserConfiguration implements BrowserConfiguration {
	
	public String getBaseUrlRegex() {
		String baseUrl = getBaseUrl();
		return baseUrl == null ? null : Pattern.quote(baseUrl);
	}
	
	public Boolean isUrlVerification() {
		return true;
	}
	
	public WebDriver getDriver() {
		return new FirefoxDriver();
	}
	
	public Double getWaitTimeout() {
		return 5d;
	}
	
	public Double getWaitRetryInterval() {
		return 0.1;
	}
	
	public Boolean isReported() {
		return true;
	}
	
	public File getReportDir() {
		return new File("selenium-browser-report");
	}
	
	public List<BrowserListener> getListeners() {
		List<BrowserListener> listeners = new ArrayList<>();
		listeners.add(new PageSourceListener());
		listeners.add(new ScreenshotListener());
		return listeners;
	}
	
}
