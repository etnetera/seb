package com.etnetera.qa.seleniumbrowser.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.etnetera.qa.seleniumbrowser.browser.BrowserException;

/**
 * {@link Properties} based source. It allows to load properties
 * from resource, path, file or {@link Properties} directly.
 */
public interface PropertiesSource extends PropertySource {
	
	public static Properties loadProperties(Object source) {
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

	public static Properties loadProperties(Properties properties) {
		return properties;
	}

	public static Properties loadProperties(File file) {
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

	public static Properties loadProperties(String resourceName) {
		InputStream is = PropertiesSource.class.getClassLoader().getResourceAsStream(resourceName);
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
	
	public Properties getProperties();
	
	@Override
	public default String getProperty(String key) {
		return getProperties().getProperty(key);
	}

}
