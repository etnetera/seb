package com.etnetera.qa.seleniumbrowser.browser;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.Sleeper;

import com.etnetera.qa.seleniumbrowser.configuration.BrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.context.VerificationException;
import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.logic.Logic;
import com.etnetera.qa.seleniumbrowser.module.Module;
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
	
	/**
	 * Returns actual page or null if browser
	 * has no page initiated.
	 * 
	 * @return The actual page or null
	 */
	public default Page getPage() {
		return getBrowser().getPage();
	}
	
	/**
	 * Returns actual page casted to given
	 * type or null if browser has no page 
	 * initiated.
	 * 
	 * @param page The class to cast the page to
	 * @return The actual page or null
	 */
	public default <T> T getPage(Class<T> page) {
		return getBrowser().getPage(page);
	}

	/**
	 * Sets actual browser page.
	 * 
	 * @param page The page to set
	 */
	public default void setPage(Page page) {
		getBrowser().setPage(page);
	}
	
	/**
	 * Go to specific URL.
	 * 
	 * @param url The URL to go to
	 */
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
	
	/**
	 * Constructs new wait instance using default
	 * waiting settings with specific clock to use 
	 * when measuring the timeout, specific sleeper to use
	 * to put the thread to sleep between evaluation loops,
	 * specific timeout in seconds and retry interval in seconds.
	 * 
	 * @param clock The clock to use when measuring the timeout.
	 * @param sleeper Used to put the thread to sleep between evaluation loops.
	 * @param timeout The timeout before waiting ends.
	 * @param retryInterval The retry interval for delays between condition check.
	 * @return The wait instance
	 */
	public default BrowserContextWait waiting(Clock clock, Sleeper sleeper, double timeout, double retryInterval) {
		return new BrowserContextWait(this, clock, sleeper, timeout, retryInterval);
	}

	/**
	 * Constructs new wait instance using default
	 * waiting settings with specific timeout in seconds
	 * and retry interval in seconds.
	 * 
	 * @return The wait instance
	 */
	public default BrowserContextWait waiting(double timeout, double retryInterval) {
		return new BrowserContextWait(this, timeout, retryInterval);
	}

	/**
	 * Constructs new wait instance using default
	 * waiting settings with specific timeout in seconds.
	 * 
	 * @return The wait instance
	 */
	public default BrowserContextWait waiting(double timeout) {
		return new BrowserContextWait(this, timeout);
	}

	/**
	 * Constructs new wait instance using default
	 * waiting settings.
	 * 
	 * @return The wait instance
	 */
	public default BrowserContextWait waiting() {
		return new BrowserContextWait(this);
	}

	/**
	 * Check if actual page is a given class.
	 * It performs no other redirection or verification.
	 * 
	 * @param page The class to check the page against
	 * @return true if actual page is instance of given class
	 */
	public default boolean isAt(Class<?> page) {
		return getBrowser().isAt(page);
	}
	
	/**
	 * Asserts that actual page is a given class.
	 * It performs no other redirection or verification.
	 * Throws {@link AssertionError} if condition did not
	 * pass.
	 * 
	 * @param page The class to check the page against
	 */
	public default void assertAt(Class<?> page) {
		getBrowser().assertAt(page);
	}
	
	/**
	 * Safely go to page defined by class.
	 * Page is automatically verified against
	 * its conditions. If verification fails
	 * null is returned.
	 * 
	 * @param page The page class to go to
	 * @return The page instance or null if page is not verified
	 */
	public default <T extends Page> T goToSafely(Class<T> page) {
		return getBrowser().goToSafely(page);
	}
	
	/**
	 * Safely go to already constructed page.
	 * Page is automatically verified against
	 * its conditions. If verification fails
	 * null is returned.
	 * 
	 * @param page The page instance to go to
	 * @return The same page instance or null if page is not verified
	 */
	public default <T extends Page> T goToSafely(T page) {
		return getBrowser().goToSafely(page);
	}
	
	/**
	 * Safely initialize page defined by class.
	 * It stays on same URL and tries to initialize
	 * the page. Page is automatically verified against
	 * its conditions. If verification fails
	 * null is returned.
	 * 
	 * @param page The page class to initialize
	 * @return The page instance or null if page is not verified
	 */
	public default <T extends Page> T initPageSafely(Class<T> page) {
		return getBrowser().initPageSafely(page);
	}
	
	/**
	 * Safely initialize already constructed page.
	 * It stays on same URL and tries to initialize
	 * the page. Page is automatically verified against
	 * its conditions. If verification fails
	 * null is returned.
	 * 
	 * @param page The page instance to initialize
	 * @return The same page instance or null if page is not verified
	 */
	public default <T extends Page> T initPageSafely(T page) {
		return getBrowser().initPageSafely(page);
	}
	
	/**
	 * Safely initialize first of matching page. Pages
	 * can be defined as class or as {@link Page} instances.
	 * It iterates over all pages and tries to initialize
	 * and verify them. First verified page is returned.
	 * If none of the pages is verified null is returned.
	 * 
	 * @param pages The page classes or pages to initialize
	 * @return The same page instance or null if none of pages is verified
	 */
	public default Page initOnePageSafely(Object ... pages) {
		return getBrowser().initOnePageSafely(pages);
	}
	
	/**
	 * Go to page defined by class.
	 * Page is automatically verified against
	 * its conditions.
	 * 
	 * @param page The page class to go to
	 * @return The page instance
	 * @throws VerificationException if page verification did not pass 
	 * after initialization
	 */
	public default <T extends Page> T goTo(Class<T> page) {
		return getBrowser().goTo(page);
	}
	
	/**
	 * Go to already constructed page.
	 * Page is automatically verified against
	 * its conditions.
	 * 
	 * @param page The page instance to go to
	 * @return The same page instance
	 * @throws VerificationException if page verification did not pass 
	 * after initialization
	 */
	public default <T extends Page> T goTo(T page) {
		return getBrowser().goTo(page);
	}
	
	/**
	 * Initialize page defined by class.
	 * It stays on same URL and tries to initialize
	 * the page. Page is automatically verified against
	 * its conditions.
	 * 
	 * @param page The page class to initialize
	 * @return The page instance
	 * @throws VerificationException if page verification did not pass 
	 * after initialization
	 */
	public default <T extends Page> T initPage(Class<T> page) {
		return getBrowser().initPage(page);
	}
	
	/**
	 * Initialize already constructed page.
	 * It stays on same URL and tries to initialize
	 * the page. Page is automatically verified against
	 * its conditions.
	 * 
	 * @param page The page instance to initialize
	 * @return The same page instance
	 * @throws VerificationException if page verification did not pass 
	 * after initialization
	 */
	public default <T extends Page> T initPage(T page) {
		return getBrowser().initPage(page);
	}
	
	/**
	 * Initialize first of matching page. Pages
	 * can be defined as class or as {@link Page} instances.
	 * It iterates over all pages and tries to initialize
	 * and verify them. First verified page is returned.
	 * If none of the pages is verified {@link VerificationException}
	 * is thrown. 
	 * 
	 * @param pages The page classes or pages to initialize
	 * @return The same page instance
	 * @throws VerificationException if none of pages did not pass 
	 * verification after initialization
	 */
	public default Page initOnePage(Object ... pages) {
		return getBrowser().initOnePage(pages);
	}
	
	/**
	 * Construct page defined by class.
	 * 
	 * @param page The page class to construct
	 * @return The page instance
	 */
	public default <T extends Page> T constructPage(Class<T> page) {
		return getBrowser().constructPage(page);
	}
	
	public default <T extends Module> T initModule(T module) {
		return getBrowser().initModule(module);
	}
	
	public default <T extends Module> T initModule(Class<T> module, WebElement element) {
		return getBrowser().initModule(module, this, element);
	}
	
	public default <T extends Module> T constructModule(Class<T> module, WebElement element) {
		return getBrowser().constructModule(module, this, element);
	}
	
	public default <T extends Logic> T initLogic(T logic) {
		return getBrowser().initLogic(logic);
	}
	
	public default <T extends Logic> T initLogic(Class<T> logic) {
		return getBrowser().initLogic(logic, this);
	}
	
	public default <T extends Logic> T constructLogic(Class<T> logic) {
		return getBrowser().constructLogic(logic, this);
	}
	
	public default void initElements() {
		getBrowser().initElements(this);
	}
	
	public default boolean isPresent(WebElement element) {
		return getBrowser().isPresent(element);
	}
	
	public default boolean isNotPresent(WebElement element) {
		return getBrowser().isNotPresent(element);
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
	
	public default BrowserUtils getUtils() {
		return getBrowser().getUtils();
	}

	public default Map<String, Object> getDataHolder() {
		return getBrowser().getDataHolder();
	}

	public default String getProperty(String key) {
		return getBrowser().getProperty(key);
	}

	public default <T extends WebElement> T findElement(By by, Class<T> elementCls) {
		return getBrowser().findElement(this, by, elementCls, false);
	}
	
	public default WebElement findElement(SearchContext context, By by) {
		return getBrowser().findElement(context, by, WebElement.class, false);
	}
	
	public default <T extends WebElement> T findElement(SearchContext context, By by, Class<T> elementCls) {
		return getBrowser().findElement(context, by, elementCls, false);
	}
	
	public default <T extends WebElement> T findElement(SearchContext context, By by, Class<T> elementCls, boolean optional) {
		return getBrowser().findElement(context, by, elementCls, optional);
	}
	
	public default <T extends WebElement> List<T> findElements(By by, Class<T> elementCls) {
		return getBrowser().findElements(this, by, elementCls);
	}
	
	public default <T extends WebElement> List<T> findElements(SearchContext context, By by, Class<T> elementCls) {
		return getBrowser().findElements(context, by, elementCls);
	}
	
	public default WebElement findOptionalElement(By by) {
		return getBrowser().findElement(this, by, WebElement.class, true);
	}
	
	public default <T extends WebElement> T findOptionalElement(By by, Class<T> elementCls) {
		return getBrowser().findElement(this, by, elementCls, true);
	}
	
	public default WebElement findOptionalElement(SearchContext context, By by) {
		return getBrowser().findElement(context, by, WebElement.class, true);
	}
	
	public default <T extends WebElement> T findOptionalElement(SearchContext context, By by, Class<T> elementCls) {
		return getBrowser().findElement(context, by, elementCls, true);
	}
	
}
