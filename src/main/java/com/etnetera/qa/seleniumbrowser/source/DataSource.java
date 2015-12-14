package com.etnetera.qa.seleniumbrowser.source;

import java.util.Map;

/**
 * Data source. You can add whatever data type.
 */
public interface DataSource {
	
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
