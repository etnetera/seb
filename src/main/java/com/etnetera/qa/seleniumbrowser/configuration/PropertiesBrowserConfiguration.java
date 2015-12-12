package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import com.etnetera.qa.seleniumbrowser.browser.BrowserException;

/**
 * {@link Properties} based browser configuration. It allows to load properties
 * from resource, path, file or {@link Properties} directly.
 */
public class PropertiesBrowserConfiguration implements PropertyBrowserConfiguration {

	public static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "seleniumbrowser.properties";
	
	public static final String SYSTEM_PROPERTIES_KEY = "system";
	
	public static final String DEFAULT_PROPERTIES_KEY = "default";
	
	protected List<PropertiesValue> propertiesList = new ArrayList<>();

	public List<Properties> getProperties() {
		return propertiesList.stream().map(v -> v.getProperties()).collect(Collectors.toList());
	}
	
	public Map<String, Properties> getPropertiesWithKeys() {
		return propertiesList.stream()
				.collect(Collectors.toMap(PropertiesValue::getKey, PropertiesValue::getProperties));
	}

	public Properties getMergedProperties() {
		Properties props = new Properties();
		propertiesList.stream().sorted(Collections.reverseOrder()).forEach(v -> props.putAll(v.getProperties()));
		return props;
	}
	
	/**
	 * Returns properties with given key.
	 * 
	 * @param key
	 *            The properties key
	 * @return The properties or null
	 */
	public Properties getProperties(String key) {
		return propertiesList.stream().filter(c -> c.equals(key)).map(v -> v.getProperties()).findFirst().orElse(null);
	}

	/**
	 * Removes properties with given key.
	 * 
	 * @param key
	 *            The properties key
	 * @return true if properties was present
	 */
	public boolean removeProperties(String key) {
		return propertiesList.remove(key);
	}
	
	/**
	 * Loads properties from resource 
	 * {@link PropertiesBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them at the end of actual properties list.
	 * 
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addDefaultProperties() {
		return addResourceProperties(DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Loads properties from resource 
	 * {@link PropertiesBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them before specific key in actual properties list.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addDefaultPropertiesBefore(String beforeKey) {
		return addResourcePropertiesBefore(beforeKey, DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Loads properties from resource 
	 * {@link PropertiesBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them after specific key in actual properties list.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addDefaultPropertiesAfter(String afterKey) {
		return addResourcePropertiesAfter(afterKey, DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Adds {@link System#getProperties()} at the end of actual properties list.
	 * 
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addSystemProperties() {
		return addProperties(SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Adds {@link System#getProperties()} before specific key in actual properties list.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addSystemPropertiesBefore(String beforeKey) {
		return addPropertiesBefore(beforeKey, SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Adds {@link System#getProperties()} after specific key in actual properties list.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addSystemPropertiesAfter(String afterKey) {
		return addPropertiesAfter(afterKey, SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Adds properties at the end of actual properties list.
	 * 
	 * @param key The properties key
	 * @param properties
	 *            The properties
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addProperties(String key, Properties properties) {
		if (properties != null) {
			removeProperties(key);
			propertiesList.add(new PropertiesValue(key, loadProperties(properties)));
		}
		return this;
	}
	
	/**
	 * Adds properties before specific key in actual properties list.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @param key The properties key
	 * @param properties
	 *            The properties
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addPropertiesBefore(String beforeKey, String key, Properties properties) {
		if (properties != null) {
			int i = propertiesList.indexOf(beforeKey);
			if (i < 0)
				throw new BrowserException("There are no properties with key " + beforeKey);
			removeProperties(key);
			propertiesList.add(i, new PropertiesValue(key, loadProperties(properties)));
		}
		return this;
	}
	
	/**
	 * Adds properties after specific key in actual properties list.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @param key The properties key
	 * @param properties
	 *            The properties
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addPropertiesAfter(String afterKey, String key, Properties properties) {
		if (properties != null) {
			int i = propertiesList.indexOf(afterKey);
			if (i < 0)
				throw new BrowserException("There are no properties with key " + afterKey);
			removeProperties(key);
			propertiesList.add(i + 1, new PropertiesValue(key, loadProperties(properties)));
		}
		return this;
	}
	
	/**
	 * Loads properties from file and adds them at the end of actual properties list.
	 * 
	 * @param key The properties key
	 * @param file
	 *            The properties file
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addFileProperties(String key, File file) {
		return addProperties(key, loadProperties(file));
	}
	
	/**
	 * Loads properties from file and adds them before specific key in actual properties list.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @param key The properties key
	 * @param file
	 *            The properties file
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addFilePropertiesBefore(String beforeKey, String key, File file) {
		return addPropertiesBefore(beforeKey, key, loadProperties(file));
	}
	
	/**
	 * Loads properties from file and adds them after specific key in actual properties list.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @param key The properties key
	 * @param file
	 *            The properties file
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addFilePropertiesAfter(String afterKey, String key, File file) {
		return addPropertiesAfter(afterKey, key, loadProperties(file));
	}
	
	/**
	 * Loads properties from resource by its name and adds them at the end of actual
	 * properties list with specific key.
	 * 
	 * @param key The properties key
	 * @param resourceName
	 *            The properties resource
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addResourceProperties(String key, String resourceName) {
		return addProperties(key, loadProperties(resourceName));
	}
	
	/**
	 * Loads properties from resource by its name and adds them before specific key in actual
	 * properties list with specific key.
	 * 
	 * @param beforeKey
	 *            The before properties key
	 * @param key The properties key
	 * @param resourceName
	 *            The properties resource
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addResourcePropertiesBefore(String beforeKey, String key, String resourceName) {
		return addPropertiesBefore(beforeKey, key, loadProperties(resourceName));
	}
	
	/**
	 * Loads properties from resource by its name and adds them after specific key in actual
	 * properties list with specific key.
	 * 
	 * @param afterKey
	 *            The after properties key
	 * @param key The properties key
	 * @param resourceName
	 *            The properties resource
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addResourcePropertiesAfter(String afterKey, String key, String resourceName) {
		return addPropertiesAfter(afterKey, key, loadProperties(resourceName));
	}
	
	/**
	 * Loads properties from resource 
	 * {@link PropertiesBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and pushes them at the start of actual properties list.
	 * 
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushDefaultProperties() {
		return pushResourceProperties(DEFAULT_PROPERTIES_KEY, DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Pushes {@link System#getProperties()} at the start of actual properties list.
	 * 
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushSystemProperties() {
		return pushProperties(SYSTEM_PROPERTIES_KEY, System.getProperties());
	}
	
	/**
	 * Pushes properties at the start of actual properties list
	 * with specific key.
	 * 
	 * @param key The properties key
	 * @param properties
	 *            The properties
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushProperties(String key, Properties properties) {
		if (properties != null) {
			this.propertiesList.add(0, new PropertiesValue(key, loadProperties(properties)));
		}
		return this;
	}
	
	/**
	 * Loads properties from file and pushes them at the start of actual properties list
	 * with specific key.
	 * 
	 * @param key The properties key
	 * @param file
	 *            The properties file
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushFileProperties(String key, File file) {
		return pushProperties(key, loadProperties(file));
	}
	
	/**
	 * Loads properties from resource by its name and pushes them at the start of actual
	 * properties list with specific key.
	 * 
	 * @param key The properties key
	 * @param resourceName
	 *            The properties resource
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushResourceProperties(String key, String resourceName) {
		return pushProperties(key, loadProperties(resourceName));
	}
	
	@Override
	public String getProperty(String key) {
		if (propertiesList != null) {
			for (PropertiesValue v : propertiesList) {
				String value = v.getProperties().getProperty(key);
				if (value != null)
					return value;
			}
		}
		return null;
	}

	protected Properties loadProperties(Object source) {
		if (source instanceof Properties) {
			return loadProperties((Properties) source);
		}
		if (source instanceof File) {
			return loadProperties((File) source);
		}
		if (source instanceof String) {
			return loadProperties((String) source);
		}
		throw new BrowserException("Unsupported properties source type " + source.getClass());
	}

	protected Properties loadProperties(Properties properties) {
		return properties;
	}

	protected Properties loadProperties(File file) {
		if (!file.canRead())
			throw new BrowserException("Properties file is not readable " + file);
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		} catch (IOException e) {
			throw new BrowserException("Error while reading properties file " + file);
		}
		return properties;
	}

	protected Properties loadProperties(String resourceName) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
		if (is == null)
			throw new BrowserException("Properties resource does not exists " + resourceName);
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			throw new BrowserException("Error while reading properties resource " + resourceName);
		}
		return properties;
	}
	
	protected class PropertiesValue {
		
		protected String key;
		
		protected Properties properties;
		
		public PropertiesValue(String key, Properties properties) {
			this.key = key;
			this.properties = properties;
		}
		
		public String getKey() {
			return key;
		}

		public Properties getProperties() {
			return properties;
		}

		@Override
		public int hashCode() {
			return key.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return key.equals(obj);
		}
		
	}

}
