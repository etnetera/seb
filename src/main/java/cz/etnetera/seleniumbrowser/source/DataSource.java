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

import java.util.Map;

/**
 * Data source. You can add whatever data type.
 */
public interface DataSource {
	
	/**
	 * Returns map which holds data.
	 * 
	 * @return The data holder map
	 */
	public Map<String, Object> getDataHolder();
	
	/**
	 * Returns data casted to given type.
	 * 
	 * @param key The data key.
	 * @return The data or null if key is not present.
	 */
	@SuppressWarnings("unchecked")
	public default <T extends Object> T getData(String key, Class<T> cls) {
		return (T) getDataHolder().get(key);
	}
	
	/**
	 * Removes data.
	 * 
	 * @param key
	 *            The data key
	 * @return The associated data or null if key was not present.
	 */
	public default Object removeData(String key) {
		return getDataHolder().remove(key);
	}
	
	/**
	 * Adds data.
	 * 
	 * @param key The data key
	 * @param data
	 *            The data
	 * @return Same instance
	 */
	public default DataSource addData(String key, Object data) {
		getDataHolder().put(key, data);
		return this;
	}
	
}
