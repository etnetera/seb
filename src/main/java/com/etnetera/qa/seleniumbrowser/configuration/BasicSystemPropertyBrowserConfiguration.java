package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

/**
 * Basic implementation of {@link SystemPropertyBrowserConfiguration}
 * which tries to get all possible values from system properties.
 * System properties are prefixed with 'browser.':
 * 
 *     baseUrlProperty 				= browser.baseUrlProperty
 *     baseUrlRegexProperty 		= browser.baseUrlRegexProperty
 *     urlVerificationProperty 		= browser.urlVerificationProperty
 *     waitTimeoutProperty 			= browser.waitTimeoutProperty
 *     waitRetryIntervalProperty 	= browser.waitRetryIntervalProperty
 *     reportedProperty 			= browser.reportedProperty
 *     reportDirProperty 			= browser.reportDirProperty
 *     
 * For now only {@link BrowserConfiguration#getDriver()} 
 * and {@link BrowserConfiguration#getListeners()}
 * are not supported methods and returns null directly.
 */
public class BasicSystemPropertyBrowserConfiguration implements SystemPropertyBrowserConfiguration, BrowserConfiguration {
	
	protected String propertyPrefix = "browser.";
	
	protected String baseUrlProperty = "baseUrl";
	protected String baseUrlRegexProperty = "baseUrlRegex";
	
	protected String urlVerificationProperty = "urlVerification";
	
	protected String waitTimeoutProperty = "waitTimeout";
	protected String waitRetryIntervalProperty = "waitRetryInterval";
	
	protected String reportedProperty = "reported";
	protected String reportDirProperty = "reportDir";
	
	public String getBaseUrl() {
		return getProperty(baseUrlProperty);
	}
	
	public String getBaseUrlRegex() {
		return getProperty(baseUrlRegexProperty);
	}
	
	public Boolean isUrlVerification() {
		return getProperty(urlVerificationProperty, Boolean.class);
	}
	
	public WebDriver getDriver() {
		// TODO - allow set from system properties
		return null;
	}
	
	public Double getWaitTimeout() {
		return getProperty(waitTimeoutProperty, Double.class);
	}
	
	public Double getWaitRetryInterval() {
		return getProperty(waitRetryIntervalProperty, Double.class);
	}
	
	public Boolean isReported() {
		return getProperty(reportedProperty, Boolean.class);
	}
	
	public File getReportDir() {
		return getProperty(reportDirProperty, File.class);
	}
	
	public List<BrowserListener> getListeners() {
		// TODO - allow set from system properties
		return null;
	}
	
}
