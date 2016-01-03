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
import java.nio.file.Path;
import java.nio.file.Paths;

import cz.etnetera.seleniumbrowser.browser.BrowserException;

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
			T value = castString(getProperty(key), cls);
			return value == null ? def : value;
		} catch (Exception e) {
			throw new BrowserException("Unable to resolve property with type " + cls.getName() + " for key " + key);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public default <T extends Object> T castString(String value, Class<T> defCls) {
		if (value == null) return null;
		if (String.class.isAssignableFrom(defCls)) {
			return (T) value;
		}
		if (Boolean.class.isAssignableFrom(defCls)) {
			return (T) Boolean.valueOf(value);
		}
		if (Byte.class.isAssignableFrom(defCls)) {
			return (T) Byte.valueOf(value);
		}
		if (Character.class.isAssignableFrom(defCls)) {
			return (T) Character.valueOf(value.charAt(0));
		}
		if (Double.class.isAssignableFrom(defCls)) {
			return (T) Double.valueOf(value);
		}
		if (Float.class.isAssignableFrom(defCls)) {
			return (T) Float.valueOf(value);
		}
		if (Integer.class.isAssignableFrom(defCls)) {
			return (T) Integer.valueOf(value);
		}
		if (Long.class.isAssignableFrom(defCls)) {
			return (T) Long.valueOf(value);
		}
		if (Short.class.isAssignableFrom(defCls)) {
			return (T) Short.valueOf(value);
		}
		if (File.class.isAssignableFrom(defCls)) {
			return (T) new File(value);
		}
		if (Path.class.isAssignableFrom(defCls)) {
			return (T) Paths.get(value);
		}
		if (defCls.isEnum()) {
			return (T) Enum.valueOf((Class<Enum>) defCls, value);
		}
		throw new BrowserException("Unsupported string cast type " + defCls.getName() + " for value " + value);
	}
	
}
