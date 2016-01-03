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
package cz.etnetera.seleniumbrowser.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import cz.etnetera.seleniumbrowser.browser.BrowserException;

/**
 * {@link Properties} based source. It allows to load properties
 * from resource, path, file or {@link Properties} directly.
 */
public interface PropertiesSource extends PropertySource {
	
	public static final String ENCODING = "UTF-8";
	
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
			properties.load(new InputStreamReader(new FileInputStream(file), ENCODING));
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
			properties.load(new InputStreamReader(is, ENCODING));
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
