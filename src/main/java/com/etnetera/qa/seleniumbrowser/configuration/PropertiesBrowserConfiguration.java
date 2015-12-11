package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.etnetera.qa.seleniumbrowser.browser.BrowserException;

/**
 * {@link Properties} based browser configuration. It allows to load properties
 * from resource, path, file or {@link Properties} directly.
 */
public class PropertiesBrowserConfiguration implements PropertyBrowserConfiguration {

	public static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "seleniumbrowser.properties";
	
	protected List<Properties> propertiesList = new ArrayList<>();

	public List<Properties> getPropertiesList() {
		return propertiesList;
	}

	public void setPropertiesList(List<Properties> propertiesList) {
		this.propertiesList = propertiesList;
	}

	public Properties getProperties() {
		Properties props = new Properties();
		propertiesList.stream().sorted(Collections.reverseOrder()).forEach(p -> props.putAll(p));
		return props;
	}

	public void setProperties(Properties properties) {
		this.propertiesList = new ArrayList<>(Arrays.asList(properties));
	}

	@Override
	public String getProperty(String key) {
		if (propertiesList != null) {
			for (Properties props : propertiesList) {
				String value = props.getProperty(key);
				if (value != null)
					return value;
			}
		}
		return null;
	}
	
	/**
	 * Loads properties from resource 
	 * {@link PropertiesBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and adds them into actual properties list.
	 * 
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addDefaultProperties() {
		return addProperties(DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Loads properties from resource 
	 * {@link PropertiesBrowserConfiguration#DEFAULT_PROPERTIES_RESOURCE_NAME} 
	 * and pushes them into actual properties list.
	 * 
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushDefaultProperties() {
		return pushProperties(DEFAULT_PROPERTIES_RESOURCE_NAME);
	}
	
	/**
	 * Adds {@link System#getProperties()} into actual properties list.
	 * 
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addSystemProperties() {
		return addProperties(System.getProperties());
	}
	
	/**
	 * Pushes {@link System#getProperties()} into actual properties list.
	 * 
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushSystemProperties() {
		return pushProperties(System.getProperties());
	}

	/**
	 * Adds properties into actual properties list.
	 * 
	 * @param properties
	 *            The properties
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addProperties(Properties properties) {
		if (properties != null) {
			if (propertiesList == null)
				propertiesList = new ArrayList<>();
			propertiesList.add(loadProperties(properties));
		}
		return this;
	}

	/**
	 * Loads properties from file on given path and adds them into actual
	 * properties list.
	 * 
	 * @param path
	 *            The path to properties file
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addProperties(Path path) {
		return addProperties(loadProperties(path));
	}

	/**
	 * Loads properties from file and adds them into actual properties list.
	 * 
	 * @param file
	 *            The properties file
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addProperties(File file) {
		return addProperties(loadProperties(file));
	}

	/**
	 * Loads properties from resource by its name and adds them into actual
	 * properties list.
	 * 
	 * @param resourceName
	 *            The properties resource
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addProperties(String resourceName) {
		return addProperties(loadProperties(resourceName));
	}

	/**
	 * Loads properties from object convertible to all possible properties
	 * source and adds them into actual properties list. See all merge methods.
	 * 
	 * @param source
	 *            The properties source
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration addProperties(Object source) {
		return addProperties(loadProperties(source));
	}

	/**
	 * Pushes properties into actual properties list.
	 * 
	 * @param properties
	 *            The properties
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushProperties(Properties properties) {
		if (properties != null) {
			if (this.propertiesList == null)
				this.propertiesList = new ArrayList<>();
			this.propertiesList.add(0, loadProperties(properties));
		}
		return this;
	}

	/**
	 * Loads properties from file on given path and pushes them into actual
	 * properties list.
	 * 
	 * @param path
	 *            The path to properties file
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushProperties(Path path) {
		return pushProperties(loadProperties(path));
	}

	/**
	 * Loads properties from file and pushes them into actual properties list.
	 * 
	 * @param file
	 *            The properties file
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushProperties(File file) {
		return pushProperties(loadProperties(file));
	}

	/**
	 * Loads properties from resource by its name and pushes them into actual
	 * properties list.
	 * 
	 * @param resourceName
	 *            The properties resource
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushProperties(String resourceName) {
		return pushProperties(loadProperties(resourceName));
	}

	/**
	 * Loads properties from object convertible to all possible properties
	 * source and pushes them into actual properties list. See all merge
	 * methods.
	 * 
	 * @param source
	 *            The properties source
	 * @return Same instance
	 */
	public PropertiesBrowserConfiguration pushProperties(Object source) {
		return pushProperties(loadProperties(source));
	}

	protected Properties loadProperties(Object source) {
		if (source instanceof Properties) {
			return loadProperties((Properties) source);
		}
		if (source instanceof Path) {
			return loadProperties((Path) source);
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

	protected Properties loadProperties(Path path) {
		return loadProperties(path.toFile());
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

}
