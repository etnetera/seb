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
package cz.etnetera.seb.source;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import cz.etnetera.seb.SebException;

/**
 * {@link Properties} list based source. It allows to load properties
 * from resource, path, file or {@link Properties} directly
 * and chain them into one. {@link Properties} can be added
 * at the front or end of already added properties or
 * can be added before/after properties with given key.
 */
public interface ChainedPropertiesSource extends PropertySource {
	
	List<PropertiesValue> getPropertiesHolder();

	default List<Properties> getProperties() {
		return getPropertiesHolder().stream().map(v -> v.getProperties()).collect(Collectors.toList());
	}
	
	default Map<String, Properties> getPropertiesWithKeys() {
		return getPropertiesHolder().stream()
				.collect(Collectors.toMap(PropertiesValue::getKey, PropertiesValue::getProperties));
	}

	default Properties getMergedProperties() {
		Properties props = new Properties();
		getPropertiesHolder().stream().sorted(Collections.reverseOrder()).forEach(v -> props.putAll(v.getProperties()));
		return props;
	}
	
	/**
	 * Returns properties with given key.
	 * 
	 * @param key
	 *            The properties key
	 * @return The properties or null
	 */
	default Properties getProperties(String key) {
		return getPropertiesHolder().stream().filter(v -> v.equals(key)).map(v -> v.getProperties()).findFirst().orElse(null);
	}

	/**
	 * Removes properties with given key.
	 * 
	 * @param key
	 *            The properties key
	 * @return true if properties was present
	 */
	default boolean removeProperties(String key) {
		return getPropertiesHolder().remove(new PropertiesValue(key, null));
	}
	
	/**
	 * Adds properties at the end of actual properties list.
	 * 
	 * @param key The properties key
	 * @param properties
	 *            The properties
	 * @return Same instance
	 */
	default ChainedPropertiesSource addProperties(String key, Properties properties) {
		if (properties != null) {
			removeProperties(key);
			getPropertiesHolder().add(new PropertiesValue(key, PropertiesSource.loadProperties(properties)));
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
	default ChainedPropertiesSource addPropertiesBefore(String beforeKey, String key, Properties properties) {
		if (properties != null) {
			int i = getPropertiesHolder().indexOf(new PropertiesValue(beforeKey, null));
			if (i < 0)
				throw new SebException("There are no properties with key " + beforeKey);
			removeProperties(key);
			getPropertiesHolder().add(i, new PropertiesValue(key, PropertiesSource.loadProperties(properties)));
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
	default ChainedPropertiesSource addPropertiesAfter(String afterKey, String key, Properties properties) {
		if (properties != null) {
			int i = getPropertiesHolder().indexOf(new PropertiesValue(afterKey, null));
			if (i < 0)
				throw new SebException("There are no properties with key " + afterKey);
			removeProperties(key);
			getPropertiesHolder().add(i + 1, new PropertiesValue(key, PropertiesSource.loadProperties(properties)));
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
	default ChainedPropertiesSource addFileProperties(String key, File file) {
		return addProperties(key, PropertiesSource.loadProperties(file));
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
	default ChainedPropertiesSource addFilePropertiesBefore(String beforeKey, String key, File file) {
		return addPropertiesBefore(beforeKey, key, PropertiesSource.loadProperties(file));
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
	default ChainedPropertiesSource addFilePropertiesAfter(String afterKey, String key, File file) {
		return addPropertiesAfter(afterKey, key, PropertiesSource.loadProperties(file));
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
	default ChainedPropertiesSource addResourceProperties(String key, String resourceName) {
		return addProperties(key, PropertiesSource.loadProperties(resourceName));
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
	default ChainedPropertiesSource addResourcePropertiesBefore(String beforeKey, String key, String resourceName) {
		return addPropertiesBefore(beforeKey, key, PropertiesSource.loadProperties(resourceName));
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
	default ChainedPropertiesSource addResourcePropertiesAfter(String afterKey, String key, String resourceName) {
		return addPropertiesAfter(afterKey, key, PropertiesSource.loadProperties(resourceName));
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
	default ChainedPropertiesSource pushProperties(String key, Properties properties) {
		if (properties != null) {
			this.getPropertiesHolder().add(0, new PropertiesValue(key, PropertiesSource.loadProperties(properties)));
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
	default ChainedPropertiesSource pushFileProperties(String key, File file) {
		return pushProperties(key, PropertiesSource.loadProperties(file));
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
	default ChainedPropertiesSource pushResourceProperties(String key, String resourceName) {
		return pushProperties(key, PropertiesSource.loadProperties(resourceName));
	}
	
	@Override
	default String getProperty(String key) {
		if (getPropertiesHolder() != null) {
			for (PropertiesValue v : getPropertiesHolder()) {
				String value = v.getProperties().getProperty(key);
				if (value != null)
					return value;
			}
		}
		return null;
	}
	
	static class PropertiesValue {
		
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
			if (obj instanceof PropertiesValue)
				return key.equals(((PropertiesValue) obj).getKey());
			return false;
		}
		
	}

}
