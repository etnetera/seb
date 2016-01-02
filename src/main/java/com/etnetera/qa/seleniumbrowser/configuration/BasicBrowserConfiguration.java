/* Copyright 2016 Etnetera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;
import com.etnetera.qa.seleniumbrowser.listener.impl.PageSourceListener;
import com.etnetera.qa.seleniumbrowser.listener.impl.ScreenshotListener;
import com.etnetera.qa.seleniumbrowser.source.ChainedPropertiesSource;
import com.etnetera.qa.seleniumbrowser.source.DataSource;

/**
 * Configures browser.
 * You are free to add whatever methods you want
 * as you can easily get overridden configuration
 * {@link BrowserContext#getConfiguration(Class)}.
 * 
 * It allows to load properties
 * from resource, path, file or {@link Properties} directly
 * and it has higher priority than default values. You can
 * obtain them later using {@link BrowserContext#getProperty(String)}
 * and similar property based methods.
 * 
 * Tries to get all possible values from chained
 * properties. Properties are prefixed with 'browser.':
 * 
 *     baseUrlProperty 				= browser.baseUrlProperty
 *     baseUrlRegexProperty 		= browser.baseUrlRegexProperty
 *     urlVerificationProperty 		= browser.urlVerificationProperty
 *     waitTimeoutProperty 			= browser.waitTimeoutProperty
 *     waitRetryIntervalProperty 	= browser.waitRetryIntervalProperty
 *     reportedProperty 			= browser.reportedProperty
 *     reportDirProperty 			= browser.reportDirProperty
 *     
 * For now only {@link BasicBrowserConfiguration#getDriver(DesiredCapabilities)}, 
 * {@link BasicBrowserConfiguration#getCapabilities()}  
 * and {@link BasicBrowserConfiguration#getListeners()}
 * are not supported methods and calls default
 * methods directly.
 * 
 * In addition you can add any data using {@link BasicBrowserConfiguration#addData(String, Object)}
 * and obtain it using {@link BrowserContext#getData(String, Class)}.
 */
public class BasicBrowserConfiguration implements BrowserConfiguration, ChainedPropertiesSource, DataSource {
	
	public static final String SYSTEM_PROPERTIES_KEY = "system";
	
	public static final String DEFAULT_PROPERTIES_KEY = "default";
	public static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "seleniumbrowser.properties";
	
	public static final String PREFIX = "browser.";
	
	public static final String BASE_URL = PREFIX + "baseUrl";
	public static final String BASE_URL_REGEX = PREFIX + "baseUrlRegex";
	
	public static final String URL_VERIFICATION = PREFIX + "urlVerification";
	
	public static final String WAIT_TIMEOUT = PREFIX + "waitTimeout";
	public static final String WAIT_RETRY_INTERVAL = PREFIX + "waitRetryInterval";
	
	public static final String REPORTED = PREFIX + "reported";
	public static final String REPORT_DIR = PREFIX + "reportDir";
	
	protected List<PropertiesValue> propertiesHolder = new ArrayList<>();
	
	protected Map<String, Object> dataHolder = new HashMap<String, Object>();
	
	/**
	 * Constructs new instance with system and default properties.
	 */
	public BasicBrowserConfiguration() {
		addSystemProperties().addDefaultProperties();
	}

	/**
	 * Default base URL for pages.
	 * Override this for different value.
	 * 
	 * @return The base URL
	 */
	protected String getDefaultBaseUrl() {
		return null;
	}
	
	/**
	 * Default base URL regex for pages.
	 * Override this for different value.
	 * 
	 * @return The base URL regex
	 */
	protected String getDefaultBaseUrlRegex() {
		String baseUrl = getBaseUrl();
		return baseUrl == null ? null : Pattern.quote(baseUrl);
	}
	
	/**
	 * Is URL on pages verified as default?
	 * Override this for different value.
	 * 
	 * @return Verification status
	 */
	protected boolean isDefaultUrlVerification() {
		return true;
	}
	
	/**
	 * Returns default {@link WebDriver} instance.
	 * Override this for different value.
	 * 
	 * @param caps The non null desired capabilities.
	 * @return The driver
	 */
	protected WebDriver getDefaultDriver(DesiredCapabilities caps) {
		return new FirefoxDriver(caps);
	}
	
	/**
	 * Returns optional default {@link DesiredCapabilities}.
	 * Override this for different value.
	 * 
	 * @return The desired capabilities.
	 */
	protected DesiredCapabilities getDefaultCapabilities() {
		return null;
	}
	
	/**
	 * Returns default wait timeout.
	 * Override this for different value.
	 * 
	 * @return The wait timeout.
	 */
	protected double getDefaultWaitTimeout() {
		return 5;
	}
	
	/**
	 * Returns default wait retry interval.
	 * Override this for different value.
	 * 
	 * @return The wait retry interval.
	 */
	protected double getDefaultWaitRetryInterval() {
		return 0.1;
	}
	
	/**
	 * Is storing files using browser enabled as default?
	 * Override this for different value.
	 * 
	 * @return Reporting status.
	 */
	protected boolean isDefaultReported() {
		return false;
	}
	
	/**
	 * Returns default directory for storing browser files.
	 * Override this for different value.
	 * 
	 * @return The report directory.
	 */
	protected File getDefaultReportDir() {
		return new File("selenium-browser-report");
	}
	
	/**
	 * Returns default list of browser event listeners.
	 * Override this for different value.
	 * 
	 * @return The browser event listeners.
	 */
	protected List<BrowserListener> getDefaultListeners() {
		return new ArrayList<>(Arrays.asList(new PageSourceListener(), new ScreenshotListener()));
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public String getBaseUrl() {
		return getProperty(BASE_URL, getDefaultBaseUrl());
	}
	
	@Override
	public String getBaseUrlRegex() {
		return getProperty(BASE_URL_REGEX, getDefaultBaseUrl());
	}
	
	@Override
	public boolean isUrlVerification() {
		return getProperty(URL_VERIFICATION, Boolean.class, isDefaultUrlVerification());
	}
	
	@Override
	public WebDriver getDriver(DesiredCapabilities caps) {
		return getDefaultDriver(caps);
	}
	
	@Override
	public DesiredCapabilities getCapabilities() {
		return getDefaultCapabilities();
	}
	
	@Override
	public double getWaitTimeout() {
		return getProperty(WAIT_TIMEOUT, Double.class, getDefaultWaitTimeout());
	}
	
	@Override
	public double getWaitRetryInterval() {
		return getProperty(WAIT_RETRY_INTERVAL, Double.class, getDefaultWaitRetryInterval());
	}
	
	@Override
	public boolean isReported() {
		return getProperty(REPORTED, Boolean.class, isDefaultReported());
	}
	
	@Override
	public File getReportDir() {
		String reportDir = getProperty(REPORT_DIR);
		return reportDir == null ? getDefaultReportDir() : new File(reportDir);
	}
	
	@Override
	public List<BrowserListener> getListeners() {
		return getDefaultListeners();
	}

	@Override
	public List<PropertiesValue> getPropertiesHolder() {
		return propertiesHolder;
	}
	
	@Override
	public Map<String, Object> getDataHolder() {
		return dataHolder;
	}
	
	/**
	 * Loads properties from resource 
	 * {@link BasicBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them at the end of actual properties list.
	 * 
	 * @return Same instance
	 */
	public BasicBrowserConfiguration addDefaultProperties() {
		return (BasicBrowserConfiguration) addResourceProperties(DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Loads properties from resource 
	 * {@link BasicBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them before specific key in actual properties list.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @return Same instance
	 */
	public BasicBrowserConfiguration addDefaultPropertiesBefore(String beforeKey) {
		return (BasicBrowserConfiguration) addResourcePropertiesBefore(beforeKey, DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Loads properties from resource 
	 * {@link BasicBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them after specific key in actual properties list.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @return Same instance
	 */
	public BasicBrowserConfiguration addDefaultPropertiesAfter(String afterKey) {
		return (BasicBrowserConfiguration) addResourcePropertiesAfter(afterKey, DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Adds {@link System#getProperties()} at the end of actual properties list.
	 * 
	 * @return Same instance
	 */
	public BasicBrowserConfiguration addSystemProperties() {
		return (BasicBrowserConfiguration) addProperties(SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Adds {@link System#getProperties()} before specific key in actual properties list.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @return Same instance
	 */
	public BasicBrowserConfiguration addSystemPropertiesBefore(String beforeKey) {
		return (BasicBrowserConfiguration) addPropertiesBefore(beforeKey, SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Adds {@link System#getProperties()} after specific key in actual properties list.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @return Same instance
	 */
	public BasicBrowserConfiguration addSystemPropertiesAfter(String afterKey) {
		return (BasicBrowserConfiguration) addPropertiesAfter(afterKey, SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Loads properties from resource 
	 * {@link BasicBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and pushes them at the start of actual properties list.
	 * 
	 * @return Same instance
	 */
	public BasicBrowserConfiguration pushDefaultProperties() {
		return (BasicBrowserConfiguration) pushResourceProperties(DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Pushes {@link System#getProperties()} at the start of actual properties list.
	 * 
	 * @return Same instance
	 */
	public BasicBrowserConfiguration pushSystemProperties() {
		return (BasicBrowserConfiguration) pushProperties(SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
}
