package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

/**
 * Chained browser configuration interface which allows combine more
 * configurations in one. It supports property base configurations as well.
 */
public class ChainedBrowserConfiguration implements BrowserConfiguration, PropertyBrowserConfiguration {

	protected List<Object> configurations = new ArrayList<>();
	
	public List<Object> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<Object> configurations) {
		this.configurations = configurations;
	}

	/**
	 * Adds configuration into actual configurations list.
	 * 
	 * @param configuration
	 *            The configuration
	 * @return Same instance
	 */
	public ChainedBrowserConfiguration addConfiguration(Object configuration) {
		if (configuration != null) {
			if (configurations == null)
				configurations = new ArrayList<>();
			configurations.add(configuration);
		}
		return this;
	}

	/**
	 * Pushes configuration into actual configurations list.
	 * 
	 * @param configuration
	 *            The configuration
	 * @return Same instance
	 */
	public ChainedBrowserConfiguration pushConfiguration(Object configuration) {
		if (configuration != null) {
			if (configurations == null)
				configurations = new ArrayList<>();
			configurations.add(0, configuration);
		}
		return this;
	}

	@Override
	public String getProperty(String key) {
		return getChainedValue(PropertyBrowserConfiguration.class, c -> c.getProperty(key));
	}

	@Override
	public String getBaseUrl() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getBaseUrl());
	}

	@Override
	public String getBaseUrlRegex() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getBaseUrlRegex());
	}

	@Override
	public Boolean isUrlVerification() {
		return getChainedValue(BrowserConfiguration.class, c -> c.isUrlVerification());
	}

	@Override
	public WebDriver getDriver(DesiredCapabilities caps) {
		return getChainedValue(BrowserConfiguration.class, c -> c.getDriver(caps));
	}
	
	@Override
	public DesiredCapabilities getCapabilities() {
		List<DesiredCapabilities> list = collectValues(BrowserConfiguration.class, c -> c.getCapabilities());
		if (list != null) {
			Collections.reverse(list);
			return new DesiredCapabilities((Capabilities[]) list.toArray());
		}
		return null;
	}

	@Override
	public Double getWaitTimeout() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getWaitTimeout());
	}

	@Override
	public Double getWaitRetryInterval() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getWaitRetryInterval());
	}

	@Override
	public Boolean isReported() {
		return getChainedValue(BrowserConfiguration.class, c -> c.isReported());
	}

	@Override
	public File getReportDir() {
		return getChainedValue(BrowserConfiguration.class, c -> c.getReportDir());
	}

	@Override
	public List<BrowserListener> getListeners() {
		return (List<BrowserListener>) getChainedValues(BrowserConfiguration.class, BrowserListener.class, c -> c.getListeners());
	}
	
	/**
	 * Iterates over {@link ChainedBrowserConfiguration#getConfigurations()} and checks
	 * if configuration is same or subclass of configCls. If so it applies
	 * getter and if value is not null is returned. If there is no value in any
	 * configuration null is returned.
	 * 
	 * @param configCls
	 *            The configuration class which is searched for value.
	 * @param getter
	 *            The getter for obtaining the value.
	 * @return The value with type specified in getter return type or null.
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Object, V extends Object> V getChainedValue(Class<T> configCls, Function<T, V> getter) {
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
	 * Iterates over {@link ChainedBrowserConfiguration#getConfigurations()} and checks
	 * if configuration is same or subclass of configCls. If so it applies
	 * getter and if value is not null than is combined with previous values
	 * into one {@link Collection} with cls type. If there is no value in any
	 * configuration null is returned.
	 * 
	 * @param configCls
	 *            The configuration class which is searched for value.
	 * @param getter
	 *            The getter for obtaining the value.
	 * @return The list with item type specified in cls parameter or null.
	 */
	@SuppressWarnings("unchecked")
	protected <S extends Object, T extends Object, V extends Object> Collection<S> getChainedValues(Class<T> configCls,
			Class<S> cls, Function<T, V> getter) {
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

	/**
	 * Iterates over {@link ChainedBrowserConfiguration#getConfigurations()} and checks
	 * if configuration is same or subclass of configCls. If so it applies
	 * getter and if value is not null than is added into returned list. If
	 * there is no value in any configuration null is returned.
	 * 
	 * @param configCls
	 *            The configuration class which is searched for value.
	 * @param getter
	 *            The getter for obtaining the value.
	 * @return The list with values with type specified in getter return type or
	 *         null.
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Object, V extends Object> List<V> collectValues(Class<T> configCls, Function<T, V> getter) {
		List<V> list = null;
		for (Object config : getConfigurations()) {
			if (configCls.isAssignableFrom(config.getClass())) {
				V value = getter.apply((T) config);
				if (value != null) {
					if (list == null)
						list = new ArrayList<>();
					list.add(value);
				}
			}
		}
		return list;
	}

}
