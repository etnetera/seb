package com.etnetera.qa.seleniumbrowser.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Chained browser configuration interface
 * which allows combine more configurations in one.
 * See {@link BasicChainedBrowserConfiguration} which
 * implements this interface and takes {@link BrowserConfiguration}
 * contract.
 */
public interface ChainedBrowserConfiguration {
	
	/**
	 * Get all configuration objects.
	 * It is really up to you which objects will
	 * be used as configuration.
	 * 
	 * @return The configurations.
	 */
	public List<Object> getConfigurations();
	
	/**
	 * Iterates over {@link ChainedBrowserConfiguration#getConfigurations()}
	 * and checks if configuration is same or subclass of configCls.
	 * If so it applies getter and if value is not null is returned.
	 * If there is no value in any configuration null is returned.
	 * 
	 * @param configCls The configuration class which is searched for value.
	 * @param getter The getter for obtaining the value.
	 * @return The value with type specified in getter return type or null.
	 */
	@SuppressWarnings("unchecked")
	public default <T extends Object, V extends Object> V getChainedValue(Class<T> configCls, Function<T, V> getter) {
		for (Object config : getConfigurations()) {
			if (configCls.isAssignableFrom(config.getClass())) {
				V value = getter.apply((T) config);
				if (value != null)
					return value;
			}
		}
		return null;
	}
	
	/**
	 * Iterates over {@link ChainedBrowserConfiguration#getConfigurations()}
	 * and checks if configuration is same or subclass of configCls.
	 * If so it applies getter and if value is not null than is combined with
	 * previous values into one {@link List} with listCls type.
	 * If there is no value in any configuration null is returned.
	 * 
	 * @param configCls The configuration class which is searched for value.
	 * @param getter The getter for obtaining the value.
	 * @return The list with item type specified in listCls parameter or null.
	 */
	@SuppressWarnings("unchecked")
	public default <S extends Object, T extends Object, V extends Object> List<S> getChainedListValue(Class<T> configCls, Class<S> listCls, Function<T, V> getter) {
		List<S> list = null;
		for (Object config : getConfigurations()) {
			if (configCls.isAssignableFrom(config.getClass())) {
				V value = getter.apply((T) config);
				if (value != null) {
					if (list == null)
						list = new ArrayList<>();
					list.addAll((Collection<S>) value);
				}
			}
		}
		return list;
	}
	
}
