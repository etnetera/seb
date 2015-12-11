package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etnetera.qa.seleniumbrowser.browser.BrowserException;
import com.etnetera.qa.seleniumbrowser.browser.BrowserUtils;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

/**
 * Property based browser configuration.
 * Tries to get all possible values from 
 * {@link PropertyBrowserConfiguration#getProperty(String)}.
 * Properties are prefixed with 'browser.':
 * 
 *     baseUrlProperty 				= browser.baseUrlProperty
 *     baseUrlRegexProperty 		= browser.baseUrlRegexProperty
 *     urlVerificationProperty 		= browser.urlVerificationProperty
 *     waitTimeoutProperty 			= browser.waitTimeoutProperty
 *     waitRetryIntervalProperty 	= browser.waitRetryIntervalProperty
 *     reportedProperty 			= browser.reportedProperty
 *     reportDirProperty 			= browser.reportDirProperty
 *     
 * For now only {@link BrowserConfiguration#getDriver(DesiredCapabilities)}, 
 * {@link BrowserConfiguration#getCapabilities()}  
 * and {@link BrowserConfiguration#getListeners()}
 * are not supported methods and returns null directly.
 */
public interface PropertyBrowserConfiguration extends BrowserConfiguration {
	
	public static final String PREFIX = "browser.";
	
	public static final String BASE_URL = PREFIX + "baseUrl";
	public static final String BASE_URL_REGEX = PREFIX + "baseUrlRegex";
	
	public static final String URL_VERIFICATION = PREFIX + "urlVerification";
	
	public static final String WAIT_TIMEOUT = PREFIX + "waitTimeout";
	public static final String WAIT_RETRY_INTERVAL = PREFIX + "waitRetryInterval";
	
	public static final String REPORTED = PREFIX + "reported";
	public static final String REPORTED_DIR = PREFIX + "reportDir";
	
	/**
	 * Returns property as {@link String}.
	 * 
	 * @param key The property key.
	 * @return The property value or null.
	 */
	public String getProperty(String key);
	
	/**
	 * Returns property as {@link String}.
	 * If not found default value is returned.
	 * 
	 * @param key The property key.
	 * @param def The default value to return.
	 * @return The property value or default value.
	 */
	public default String getProperty(String key, String def) {
		String value = getProperty(key);
		return value == null ? def : value;
	}
	
	/**
	 * Returns property casted to given type.
	 * 
	 * @param key The property key.
	 * @param cls The type for returned value.
	 * @return The property value or null.
	 */
	public default <T extends Object> T getProperty(String key, Class<T> cls) {
		return getProperty(key, cls, null);
	}
	
	/**
	 * Returns property casted to given type.
	 * If not found default value is returned.
	 * 
	 * @param key The property key.
	 * @param cls The type for returned value.
	 * @param def The default value to return.
	 * @return The property value or default value.
	 */
	public default <T extends Object> T getProperty(String key, Class<T> cls, T def) {
		try {
			T value = BrowserUtils.castString(getProperty(key), cls);
			return value == null ? def : value;
		} catch (Exception e) {
			throw new BrowserException("Unable to resolve property with type " + cls.getName() + " for key " + key);
		}
	}
	
	@Override
	public default String getBaseUrl() {
		return getProperty(BASE_URL);
	}
	
	@Override
	public default String getBaseUrlRegex() {
		return getProperty(BASE_URL_REGEX);
	}
	
	@Override
	public default Boolean isUrlVerification() {
		return getProperty(URL_VERIFICATION, Boolean.class);
	}
	
	@Override
	public default WebDriver getDriver(DesiredCapabilities caps) {
		// TODO - allow set from system properties
		return null;
	}
	
	@Override
	public default DesiredCapabilities getCapabilities() {
		// TODO - allow set from system properties
		return null;
	}
	
	@Override
	public default Double getWaitTimeout() {
		return getProperty(WAIT_TIMEOUT, Double.class);
	}
	
	@Override
	public default Double getWaitRetryInterval() {
		return getProperty(WAIT_RETRY_INTERVAL, Double.class);
	}
	
	@Override
	public default Boolean isReported() {
		return getProperty(REPORTED, Boolean.class);
	}
	
	@Override
	public default File getReportDir() {
		return getProperty(REPORTED_DIR, File.class);
	}
	
	@Override
	public default List<BrowserListener> getListeners() {
		// TODO - allow set from system properties
		return null;
	}
	
}
