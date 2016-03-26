/* Copyright 2016 Etnetera a.s.
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
package cz.etnetera.seb.configuration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
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
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.SebContext;
import cz.etnetera.seb.SebException;
import cz.etnetera.seb.SebUtils;
import cz.etnetera.seb.listener.SebListener;
import cz.etnetera.seb.listener.impl.PageSourceListener;
import cz.etnetera.seb.listener.impl.ScreenshotListener;
import cz.etnetera.seb.source.ChainedPropertiesSource;
import cz.etnetera.seb.source.DataSource;

/**
 * Configures Seb.
 * You are free to add whatever methods you want
 * as you can easily get overridden configuration
 * {@link SebContext#getConfiguration(Class)}.
 * 
 * It allows to load properties
 * from resource, path, file or {@link Properties} directly
 * and it has higher priority than default values. You can
 * obtain them later using {@link SebContext#getProperty(String)}
 * and similar property based methods.
 * 
 * Tries to get all possible values from chained
 * properties. Properties are prefixed with 'seb.':
 * 
 *     baseUrlProperty 				= seb.baseUrlProperty
 *     baseUrlRegexProperty 		= seb.baseUrlRegexProperty
 *     urlVerificationProperty 		= seb.urlVerificationProperty
 *     waitTimeoutProperty 			= seb.waitTimeoutProperty
 *     waitRetryIntervalProperty 	= seb.waitRetryIntervalProperty
 *     reportedProperty 			= seb.reportedProperty
 *     reportDirProperty 			= seb.reportDirProperty
 *     
 * For now only {@link BasicSebConfiguration#getDriver(DesiredCapabilities)}, 
 * {@link BasicSebConfiguration#getCapabilities()}  
 * and {@link BasicSebConfiguration#getListeners()}
 * are not supported methods and calls default
 * methods directly.
 * 
 * In addition you can add any data using {@link BasicSebConfiguration#addData(String, Object)}
 * and obtain it using {@link SebContext#getData(String, Class)}.
 */
public class BasicSebConfiguration implements SebConfiguration, ChainedPropertiesSource, DataSource {
	
	public static final String SYSTEM_PROPERTIES_KEY = "system";
	
	public static final String DEFAULT_PROPERTIES_KEY = "default";
	public static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "seb.properties";
	
	public static final String PREFIX = "seb.";
	
	public static final String BASE_URL = PREFIX + "baseUrl";
	public static final String BASE_URL_REGEX = PREFIX + "baseUrlRegex";
	
	public static final String URL_VERIFICATION = PREFIX + "urlVerification";
	
	public static final String WAIT_TIMEOUT = PREFIX + "waitTimeout";
	public static final String WAIT_RETRY_INTERVAL = PREFIX + "waitRetryInterval";
	
	public static final String REPORTED = PREFIX + "reported";
	public static final String REPORTS_ROOT_DIR = PREFIX + "reportsRootDir";
	public static final String REPORT_DIR = PREFIX + "reportDir";
	
	public static final String HUB_URL = PREFIX + "hubUrl";
	
	public static final String CAPABILITIES_PREFIX = PREFIX + "caps.";
	
	protected List<PropertiesValue> propertiesHolder = new ArrayList<>();
	
	protected Map<String, Object> dataHolder = new HashMap<String, Object>();
	
	/**
	 * Constructs new instance with system and default properties.
	 */
	public BasicSebConfiguration() {
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
	 * Default hub URL for RemoteWebDriver.
	 * Override this for different value.
	 * 
	 * @return The hub URL
	 */
	protected String getDefaultHubUrl() {
		return null;
	}
	
	/**
	 * Returns default {@link WebDriver} instance.
	 * Override this for different value.
	 * 
	 * @param caps The non null desired capabilities.
	 * @return The driver
	 */
	protected WebDriver getDefaultDriver(DesiredCapabilities caps) {
		String hubUrl = getHubUrl();
		if (hubUrl == null) {
			return new FirefoxDriver(caps);
		}
		try {
			return new RemoteWebDriver(new URL(hubUrl), caps);
		} catch (MalformedURLException e) {
			throw new SebException("Unable to construct remote webdriver.", e);
		}
	}
	
	/**
	 * Returns optional default {@link DesiredCapabilities}.
	 * They are constructed from system properties starting
	 * with {@link BasicSebConfiguration#CAPABILITIES_PREFIX}.
	 * Override this for different value.
	 * 
	 * @return The desired capabilities.
	 */
	protected DesiredCapabilities getDefaultCapabilities() {
		DesiredCapabilities caps = new DesiredCapabilities();
		getMergedProperties(entry -> entry.getKey() != null && entry.getKey().toString().startsWith(CAPABILITIES_PREFIX)).forEach((key, value) -> {
			caps.setCapability(key.toString().substring(CAPABILITIES_PREFIX.length()), value);
		});
		return caps;
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
	 * Is storing files using Seb enabled as default?
	 * Override this for different value.
	 * 
	 * @return Reporting status.
	 */
	protected boolean isDefaultReported() {
		return false;
	}
	
	/**
	 * Returns directory for storing Seb report directories.
	 * 
	 * @return The reports root directory.
	 */
	protected File getDefaultReportsRootDir() {
		return new File("seb-reports");
	}
	
	/**
	 * Returns directory for storing Seb report files.
	 * As default, unique directory is created inside
	 * reports root directory.
	 * Override this for different value.
	 * 
	 * @return The report directory.
	 */
	protected File getDefaultReportDir() {
		File reportsRootDir = getReportsRootDir();
		if (reportsRootDir == null) {
			throw new SebException("Reports root directory is null");
		}
		if (!reportsRootDir.exists()) {
			try {
				Files.createDirectories(reportsRootDir.toPath());
			} catch (IOException e) {
				throw new SebException("Reports root directory does not exists and can not be created " + reportsRootDir);
			}
		} else if (!reportsRootDir.isDirectory()) {
			throw new SebException("Reports root directory is not directory " + reportsRootDir);
		} else if (!reportsRootDir.canWrite()) {
			throw new SebException("Reports root directory is not writable " + reportsRootDir);
		}
		File reportDir = new SebUtils().getUniqueFilePath(reportsRootDir, LocalDateTime.now().format(Seb.FILE_DATE_FORMATTER), null).toFile();
		try {
			Files.createDirectories(reportDir.toPath());
		} catch (IOException e) {
			throw new SebException("Report directory can not be created " + reportDir);
		}
		return reportDir;
	}
	
	/**
	 * Returns default list of Seb event listeners.
	 * Override this for different value.
	 * 
	 * @return The Seb event listeners.
	 */
	protected List<SebListener> getDefaultListeners() {
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
	public String getHubUrl() {
		return getProperty(HUB_URL, getDefaultHubUrl());
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
	public File getReportsRootDir() {
		String dir = getProperty(REPORTS_ROOT_DIR);
		return dir == null ? getDefaultReportsRootDir() : new File(dir);
	}
	
	@Override
	public File getReportDir() {
		String dir = getProperty(REPORT_DIR);
		return dir == null ? getDefaultReportDir() : new File(dir);
	}
	
	@Override
	public List<SebListener> getListeners() {
		return getDefaultListeners();
	}

	@Override
	public boolean isAlertSupported(WebDriver driver) {
		return true;
	}

	@Override
	public boolean isLazyDriver() {
		return true;
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
	 * {@link BasicSebConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them at the end of actual properties list.
	 * 
	 * @return Same instance
	 */
	public BasicSebConfiguration addDefaultProperties() {
		return (BasicSebConfiguration) addResourceProperties(DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Loads properties from resource 
	 * {@link BasicSebConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them before specific key in actual properties list.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @return Same instance
	 */
	public BasicSebConfiguration addDefaultPropertiesBefore(String beforeKey) {
		return (BasicSebConfiguration) addResourcePropertiesBefore(beforeKey, DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Loads properties from resource 
	 * {@link BasicSebConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them after specific key in actual properties list.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @return Same instance
	 */
	public BasicSebConfiguration addDefaultPropertiesAfter(String afterKey) {
		return (BasicSebConfiguration) addResourcePropertiesAfter(afterKey, DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Adds {@link System#getProperties()} at the end of actual properties list.
	 * 
	 * @return Same instance
	 */
	public BasicSebConfiguration addSystemProperties() {
		return (BasicSebConfiguration) addProperties(SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Adds {@link System#getProperties()} before specific key in actual properties list.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @return Same instance
	 */
	public BasicSebConfiguration addSystemPropertiesBefore(String beforeKey) {
		return (BasicSebConfiguration) addPropertiesBefore(beforeKey, SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Adds {@link System#getProperties()} after specific key in actual properties list.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @return Same instance
	 */
	public BasicSebConfiguration addSystemPropertiesAfter(String afterKey) {
		return (BasicSebConfiguration) addPropertiesAfter(afterKey, SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Loads properties from resource 
	 * {@link BasicSebConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and pushes them at the start of actual properties list.
	 * 
	 * @return Same instance
	 */
	public BasicSebConfiguration pushDefaultProperties() {
		return (BasicSebConfiguration) pushResourceProperties(DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Pushes {@link System#getProperties()} at the start of actual properties list.
	 * 
	 * @return Same instance
	 */
	public BasicSebConfiguration pushSystemProperties() {
		return (BasicSebConfiguration) pushProperties(SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
}
