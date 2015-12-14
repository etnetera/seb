package com.etnetera.qa.seleniumbrowser.source;

import com.etnetera.qa.seleniumbrowser.browser.BrowserException;
import com.etnetera.qa.seleniumbrowser.browser.BrowserUtils;

/**
 * Property based source.
 */
public interface PropertySource {
	
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
	
}
