package com.etnetera.qa.seleniumbrowser.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

abstract public class BrowserConfiguration {

	protected String propertyPrefix = "browser.";
	
	protected String baseUrlProperty = "baseUrl";
	protected String baseUrlRegexProperty = "baseUrlRegex";
	
	protected String urlVerificationProperty = "urlVerification";
	
	protected String waitTimeoutProperty = "waitTimeout";
	protected String waitRetryIntervalProperty = "waitRetryInterval";
	
	protected String outputDirProperty = "outputDir";
	
	public WebDriver getDriver() {
		// TODO - allow set from system property with capabilities
		return getDriverDef();
	}
	
	public String getBaseUrl() {
		return getProperty(baseUrlProperty, getBaseUrlDef());
	}
	
	public String getBaseUrlRegex() {
		return getProperty(baseUrlRegexProperty, getBaseUrlRegexDef());
	}
	
	public boolean isUrlVerification() {
		return getProperty(urlVerificationProperty, isUrlVerificationDef());
	}
	
	public double getWaitTimeout() {
		return getProperty(waitTimeoutProperty, getWaitTimeoutDef());
	}
	
	public double getWaitRetryInterval() {
		return getProperty(waitRetryIntervalProperty, getWaitRetryIntervalDef());
	}
	
	protected File getOutputDir() {
		String outputDir = getProperty(outputDirProperty, String.class);
		return outputDir == null ? getOutputDirDef() : new File(outputDir);
	}
	
	protected List<BrowserListener> getListeners() {
		// TODO - allow set from system properties
		return getListenersDef();
	}
	
	protected WebDriver getDriverDef() {
		return new FirefoxDriver();
	}
	
	abstract protected String getBaseUrlDef();
	
	protected String getBaseUrlRegexDef() {
		return Pattern.quote(getBaseUrl());
	}
	
	protected boolean isUrlVerificationDef() {
		return true;
	}
	
	protected double getWaitTimeoutDef() {
		return 5;
	}
	
	protected double getWaitRetryIntervalDef() {
		return 0.1;
	}
	
	protected File getOutputDirDef() {
		return new File("selenium-browser-output");
	}
	
	protected List<BrowserListener> getListenersDef() {
		List<BrowserListener> listeners = new ArrayList<>();
		return listeners;
	}
	
	protected <T extends Object> T getProperty(String key, T def) {
		return getProperty(propertyPrefix, key, def);
	}
	
	protected <T extends Object> T getProperty(String key, Class<T> defCls) {
		return getProperty(propertyPrefix, key, defCls);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getProperty(String prefix, String key, T def) {
		T prop = (T) getProperty(prefix, key, def.getClass());
		if (prop == null) prop = def;
		return prop;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getProperty(String prefix, String key, Class<T> defCls) {
		String propKey = prefix + key;
		String prop = System.getProperty(propKey);
		if (prop == null) return null;
		if (String.class.isAssignableFrom(defCls)) {
			return (T) prop;
		}
		if (Integer.class.isAssignableFrom(defCls)) {
			return (T) Integer.valueOf(prop);
		}
		if (Long.class.isAssignableFrom(defCls)) {
			return (T) Long.valueOf(prop);
		}
		if (Double.class.isAssignableFrom(defCls)) {
			return (T) Double.valueOf(prop);
		}
		if (Float.class.isAssignableFrom(defCls)) {
			return (T) Float.valueOf(prop);
		}
		throw new IllegalArgumentException("Unsupported property type " + defCls.getName() + " for key " + propKey);
	}
	
}
