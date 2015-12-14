package com.etnetera.qa.seleniumbrowser.browser;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.Sleeper;

import com.etnetera.qa.seleniumbrowser.configuration.BrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.element.ElementManager;
import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.page.Page;
import com.etnetera.qa.seleniumbrowser.source.DataSource;
import com.etnetera.qa.seleniumbrowser.source.PropertySource;

public interface BrowserContext extends SearchContext, PropertySource, DataSource {

	/**
	 * Returns parent {@link BrowserContext} instance.
	 * 
	 * @return The parent browser context.
	 */
	public BrowserContext getContext();
	
	/**
	 * Returns parent {@link BrowserContext} instance casted to specific type.
	 * 
	 * @return The parent browser context.
	 */
	@SuppressWarnings("unchecked")
	public default <T> T getContext(Class<T> context) {
		return (T) getContext();
	}
	
	/**
	 * Returns {@link Browser} instance.
	 * 
	 * @return The browser
	 */
	public Browser getBrowser();
	
	/**
	 * Returns {@link Browser} instance casted to specific type.
	 * 
	 * @return The browser
	 */
	@SuppressWarnings("unchecked")
	public default <T> T getBrowser(Class<T> browser) {
		return (T) getBrowser();
	}
	
	/**
	 * Returns {@link BrowserConfiguration} instance.
	 * 
	 * @return The browser configuration
	 */
	public default BrowserConfiguration getConfiguration() {
		return getBrowser().getConfiguration();
	}
	
	/**
	 * Returns {@link BrowserConfiguration} instance casted to specific type.
	 * 
	 * @return The browser configuration
	 */
	public default <T> T getConfiguration(Class<T> configuration) {
		return getBrowser().getConfiguration(configuration);
	}
	
	/**
	 * Returns wrapped {@link WebDriver} instance.
	 * 
	 * @return The driver
	 */
	public default WebDriver getDriver() {
		return getBrowser().getDriver();
	}
	
	/**
	 * Returns wrapped {@link WebDriver} instance casted to specific type.
	 * 
	 * @return The driver
	 */
	public default <T> T getDriver(Class<T> driver) {
		return getBrowser().getDriver(driver);
	}
	
	public default Page getPage() {
		return getBrowser().getPage();
	}
	
	public default <T> T getPage(Class<T> page) {
		return getBrowser().getPage(page);
	}

	public default void setPage(Page page) {
		getBrowser().setPage(page);
	}
	
	public default void goToUrl(String url) {
		getBrowser().goToUrl(url);
	}
	
	/**
	 * Returns default wait timeout.
	 * 
	 * @return The default wait timeout.
	 */
	public default double getWaitTimeout() {
		return getBrowser().getWaitTimeout();
	}
	
	/**
	 * Returns default wait retry interval.
	 * 
	 * @return The default wait retry interval.
	 */
	public default double getWaitRetryInterval() {
		return getBrowser().getWaitRetryInterval();
	}
	
	public default BrowserContextWait waiting(Clock clock, Sleeper sleeper, double timeout, double retryInterval) {
		return new BrowserContextWait(this, clock, sleeper, timeout, retryInterval);
	}

	public default BrowserContextWait waiting(double timeout, double retryInterval) {
		return new BrowserContextWait(this, timeout, retryInterval);
	}

	public default BrowserContextWait waiting(double timeout) {
		return new BrowserContextWait(this, timeout);
	}

	public default BrowserContextWait waiting() {
		return new BrowserContextWait(this);
	}

	/**
	 * Check if actual page is a given class.
	 * It performs no other redirection or verification.
	 * 
	 * @param page
	 * @return
	 */
	public default boolean isAt(Class<?> page) {
		return getBrowser().isAt(page);
	}
	
	/**
	 * Check if actual page is a given object.
	 * It performs no other redirection or verification.
	 * 
	 * @param page
	 * @return
	 */
	public default boolean isAt(Object page) {
		return getBrowser().isAt(page);
	}
	
	/**
	 * Asserts that actual page is a given class.
	 * It performs no other redirection or verification.
	 * 
	 * @param page
	 * @return
	 */
	public default void assertAt(Class<?> page) {
		getBrowser().assertAt(page);
	}
	
	/**
	 * Asserts that actual page is a given object.
	 * It performs no other redirection or verification.
	 * 
	 * @param page
	 */
	public default void assertAt(Object page) {
		getBrowser().assertAt(page);
	}
	
	public default <T extends Page> T goToSafely(Class<T> page) {
		return getBrowser().goToSafely(page);
	}
	
	public default <T extends Page> T goToSafely(T page) {
		return getBrowser().goToSafely(page);
	}
	
	public default <T extends Page> T initPageSafely(Class<T> page) {
		return getBrowser().initPageSafely(page);
	}
	
	public default <T extends Page> T initPageSafely(T page) {
		return getBrowser().initPageSafely(page);
	}
	
	public default Page initOnePageSafely(Object ... pages) {
		return getBrowser().initOnePageSafely(pages);
	}
	
	public default <T extends Page> T goTo(Class<T> page) {
		return getBrowser().goTo(page);
	}
	
	public default <T extends Page> T goTo(T page) {
		return getBrowser().goTo(page);
	}
	
	public default <T extends Page> T initPage(Class<T> page) {
		return getBrowser().initPage(page);
	}
	
	public default <T extends Page> T initPage(T page) {
		return getBrowser().initPage(page);
	}
	
	public default Page initOnePage(Object ... pages) {
		return getBrowser().initOnePage(pages);
	}
	
	public default boolean isPresent(WebElement element) {
		return ElementManager.isPresent(element);
	}
	
	public default boolean isNotPresent(WebElement element) {
		return ElementManager.isNotPresent(element);
	}
	
	public default void report(String label) {
		getBrowser().report(this, label);
	}
	
	public default <T extends BrowserEvent> T constructEvent(Class<T> eventCls) {
		return getBrowser().constructEvent(eventCls, this);
	}
	
	public default void triggerEvent(BrowserEvent event) {
		getBrowser().triggerEvent(event);
	}
	
	public default void saveFile(String content, String name, String extension) {
		getBrowser().saveFile(content, name, extension);
	}
	
	public default void saveFile(byte[] bytes, String name, String extension) {
		getBrowser().saveFile(bytes, name, extension);
	}
	
	public default void saveFile(File file, String name, String extension) {
		getBrowser().saveFile(file, name, extension);
	}

	@Override
	public default Map<String, Object> getDataHolder() {
		return getBrowser().getDataHolder();
	}

	@Override
	public default String getProperty(String key) {
		return getBrowser().getProperty(key);
	}
	
}
