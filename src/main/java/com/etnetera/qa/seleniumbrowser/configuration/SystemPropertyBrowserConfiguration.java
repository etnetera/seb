package com.etnetera.qa.seleniumbrowser.configuration;

import com.etnetera.qa.seleniumbrowser.browser.BrowserException;
import com.etnetera.qa.seleniumbrowser.browser.BrowserUtils;

/**
 * System property based browser configuration.
 * See {@link BasicSystemPropertyBrowserConfiguration} which
 * implements this interface and takes {@link BrowserConfiguration}
 * contract.
 */
public interface SystemPropertyBrowserConfiguration {
	
	/**
	 * Returns property using {@link System#getProperty(String)}.
	 * Can be overridden to allow other source 
	 * than system properties.
	 * 
	 * @param key The property key.
	 * @return The property value or null.
	 */
	public default String getProperty(String key) {
		return System.getProperty(key);
	}
	
	/**
	 * Returns property using {@link System#getProperty(String)}.
	 * Property key is prefixed with prefix.
	 * 
	 * @param prefix The key prefix.
	 * @param key The property key.
	 * @return The property value or null.
	 */
	public default String getProperty(String prefix, String key) {
		return getProperty(prefix + key);
	}
	
	/**
	 * Returns property using {@link System#getProperty(String)}
	 * and casting it to given type.
	 * 
	 * @param key The property key.
	 * @param cls The type for returned value.
	 * @return The property value or null.
	 */
	public default <T extends Object> T getProperty(String key, Class<T> cls) {
		try {
			return BrowserUtils.castString(getProperty(key), cls);
		} catch (Exception e) {
			throw new BrowserException("Unable to resolve property with type " + cls.getName() + " for key " + key);
		}
	}
	
	/**
	 * Returns property using {@link System#getProperty(String)}
	 * and casting it to given type.
	 * Property key is prefixed with prefix.
	 * 
	 * @param prefix The key prefix.
	 * @param key The property key.
	 * @param cls The type for returned value.
	 * @return The property value or null.
	 */
	public default <T extends Object> T getProperty(String prefix, String key, Class<T> cls) {
		return getProperty(prefix + key, cls);
	}
	
}
