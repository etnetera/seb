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
package cz.etnetera.seleniumbrowser.browser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.Sleeper;

import cz.etnetera.seleniumbrowser.configuration.BrowserConfiguration;
import cz.etnetera.seleniumbrowser.context.VerificationException;
import cz.etnetera.seleniumbrowser.element.BrowserElement;
import cz.etnetera.seleniumbrowser.event.BrowserEvent;
import cz.etnetera.seleniumbrowser.event.impl.OnReportEvent;
import cz.etnetera.seleniumbrowser.logic.Logic;
import cz.etnetera.seleniumbrowser.page.Page;
import cz.etnetera.seleniumbrowser.source.DataSource;
import cz.etnetera.seleniumbrowser.source.PropertySource;

/**
 * This is common interface for objects holding {@link Browser}
 * and adds {@link Browser} specific methods directly in scope of
 * {@link Page}, {@link BrowserElement} and {@link Logic}.
 */
public interface BrowserContext extends SearchContext, PropertySource, DataSource, WrapsDriver {

	/**
	 * Returns parent {@link BrowserContext} instance.
	 * 
	 * @return The parent browser context.
	 */
	BrowserContext getContext();
	
	/**
	 * Returns parent {@link BrowserContext} instance casted to specific type.
	 * 
	 * @return The parent browser context.
	 */
	@SuppressWarnings("unchecked")
	default <T> T getContext(Class<T> context) {
		return (T) getContext();
	}
	
	/**
	 * Returns {@link Browser} instance.
	 * 
	 * @return The browser
	 */
	Browser getBrowser();
	
	/**
	 * Returns {@link Browser} instance casted to specific type.
	 * 
	 * @return The browser
	 */
	@SuppressWarnings("unchecked")
	default <T> T getBrowser(Class<T> browser) {
		return (T) getBrowser();
	}
	
	/**
	 * Returns {@link BrowserConfiguration} instance.
	 * 
	 * @return The browser configuration
	 */
	default BrowserConfiguration getConfiguration() {
		return getBrowser().getConfiguration();
	}
	
	/**
	 * Returns {@link BrowserConfiguration} instance casted to specific type.
	 * 
	 * @return The browser configuration
	 */
	default <T> T getConfiguration(Class<T> configuration) {
		return getBrowser().getConfiguration(configuration);
	}
	
	/**
	 * Returns wrapped {@link WebDriver} instance.
	 * 
	 * @return The driver
	 */
	default WebDriver getDriver() {
		return getBrowser().getDriver();
	}
	
	/**
	 * Returns wrapped {@link WebDriver} instance casted to specific type.
	 * 
	 * @return The driver
	 */
	default <T> T getDriver(Class<T> driver) {
		return getBrowser().getDriver(driver);
	}
	
	/**
	 * Returns actual page or null if browser
	 * has no page initiated.
	 * 
	 * @return The actual page or null
	 */
	default Page getPage() {
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
	default <T> T getPage(Class<T> page) {
		return getBrowser().getPage(page);
	}

	/**
	 * Sets actual browser page.
	 * 
	 * @param page The page to set
	 */
	default void setPage(Page page) {
		getBrowser().setPage(page);
	}
	
	/**
	 * Go to specific URL.
	 * 
	 * @param url The URL to go to
	 */
	default void goToUrl(String url) {
		getBrowser().goToUrl(url);
	}
	
	/**
	 * Returns default wait timeout.
	 * 
	 * @return The default wait timeout.
	 */
	default double getWaitTimeout() {
		return getBrowser().getWaitTimeout();
	}
	
	/**
	 * Returns default wait retry interval.
	 * 
	 * @return The default wait retry interval.
	 */
	default double getWaitRetryInterval() {
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
	default BrowserContextWait waiting(Clock clock, Sleeper sleeper, double timeout, double retryInterval) {
		return new BrowserContextWait(this, clock, sleeper, timeout, retryInterval);
	}

	/**
	 * Constructs new wait instance using default
	 * waiting settings with specific timeout in seconds
	 * and retry interval in seconds.
	 * 
	 * @return The wait instance
	 */
	default BrowserContextWait waiting(double timeout, double retryInterval) {
		return new BrowserContextWait(this, timeout, retryInterval);
	}

	/**
	 * Constructs new wait instance using default
	 * waiting settings with specific timeout in seconds.
	 * 
	 * @return The wait instance
	 */
	default BrowserContextWait waiting(double timeout) {
		return new BrowserContextWait(this, timeout);
	}

	/**
	 * Constructs new wait instance using default
	 * waiting settings.
	 * 
	 * @return The wait instance
	 */
	default BrowserContextWait waiting() {
		return new BrowserContextWait(this);
	}

	/**
	 * Check if actual page is a given class.
	 * It performs no other redirection or verification.
	 * 
	 * @param page The class to check the page against
	 * @return true if actual page is instance of given class
	 */
	default boolean isAt(Class<?> page) {
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
	default void assertAt(Class<?> page) {
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
	default <T extends Page> T goToSafely(Class<T> page) {
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
	default <T extends Page> T goToSafely(T page) {
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
	default <T extends Page> T initPageSafely(Class<T> page) {
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
	default <T extends Page> T initPageSafely(T page) {
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
	default Page initOnePageSafely(Object ... pages) {
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
	default <T extends Page> T goTo(Class<T> page) {
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
	default <T extends Page> T goTo(T page) {
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
	default <T extends Page> T initPage(Class<T> page) {
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
	default <T extends Page> T initPage(T page) {
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
	default Page initOnePage(Object ... pages) {
		return getBrowser().initOnePage(pages);
	}
	
	/**
	 * Construct page defined by class.
	 * 
	 * @param page The page class to construct
	 * @return The page instance
	 */
	default <T extends Page> T constructPage(Class<T> page) {
		return getBrowser().constructPage(page);
	}
	
	/**
	 * Initialize already constructed element.
	 * 
	 * @param element The element instance to initialize
	 * @return The same element instance
	 */
	default <T extends BrowserElement> T initBrowserElement(T element) {
		return getBrowser().initBrowserElement(element);
	}
	
	/**
	 * Initialize element defined by class.
	 * It injects this instance as context into element.
	 * 
	 * @param element The element class to initialize
	 * @param webElement The web element to wrap
	 * @param optional Is element optional
	 * @return The element instance.
	 */
	default <T extends BrowserElement> T initBrowserElement(Class<T> element, WebElement webElement, boolean optional) {
		return getBrowser().initBrowserElement(element, this, webElement, optional);
	}
	
	/**
	 * Construct element defined by class.
	 * It injects this instance as context into element.
	 * 
	 * @param element The element class to construct
	 * @param webElement The web element to wrap
	 * @param optional Is element optional
	 * @return The element instance.
	 */
	default <T extends BrowserElement> T constructBrowserElement(Class<T> element, WebElement webElement, boolean optional) {
		return getBrowser().constructBrowserElement(element, this, webElement, optional);
	}
	
	/**
	 * Initialize logic defined by class.
	 * 
	 * @param logic The logic instance to initialize
	 * @return The same logic instance
	 */
	default <T extends Logic> T initLogic(T logic) {
		return getBrowser().initLogic(logic);
	}
	
	/**
	 * Initialize logic defined by class.
	 * 
	 * @param logic The logic class to initialize
	 * @return The logic instance
	 */
	default <T extends Logic> T initLogic(Class<T> logic) {
		return getBrowser().initLogic(logic, this);
	}
	
	/**
	 * Construct logic defined by class.
	 * 
	 * @param logic The logic class to construct
	 * @return The logic instance
	 */
	default <T extends Logic> T constructLogic(Class<T> logic) {
		return getBrowser().constructLogic(logic, this);
	}
	
	/**
	 * Initialize elements annotated with {@link FindBy}
	 * and similar annotations.
	 */
	default void initElements() {
		getBrowser().initElements(this);
	}
	
	/**
	 * Checks if element exists on actual page. 
	 * If is present it does nothing. Otherwise throws
	 * {@link NoSuchElementException}.
	 * 
	 * @param element The element to test
	 * @throws NoSuchElementException if element is not present
	 */
	default void checkIfPresent(WebElement element) throws NoSuchElementException {
		getBrowser().checkIfPresent(element);
	}
	
	/**
	 * Returns true if element exists on actual page.
	 * It is opposite to {@link BrowserContext#isNotPresent(WebElement)}.
	 * 
	 * @param element The element to test
	 * @return <code>true</code> if element is present
	 */
	default boolean isPresent(WebElement element) {
		return getBrowser().isPresent(element);
	}
	
	/**
	 * Returns true if element does not exists on actual page.
	 * It is opposite to {@link BrowserContext#isPresent(WebElement)}. 
	 * 
	 * @param element The element to test
	 * @return <code>true</code> if element is not present
	 */
	default boolean isNotPresent(WebElement element) {
		return getBrowser().isNotPresent(element);
	}
	
	/**
	 * Triggers {@link OnReportEvent} with given label.
	 * 
	 * @param label The report label
	 */
	default void report(String label) {
		getBrowser().report(this, label);
	}
	
	/**
	 * Triggers {@link BrowserEvent}.
	 * 
	 * @param event The triggered event
	 */
	default void triggerEvent(BrowserEvent event) {
		getBrowser().triggerEvent(event);
	}
	
	/**
	 * Construct event defined by class.
	 * 
	 * @param eventCls The event class to construct
	 * @return The event instance
	 */
	default <T extends BrowserEvent> T constructEvent(Class<T> eventCls) {
		return getBrowser().constructEvent(eventCls, this);
	}
	
	/**
	 * Saves string into named file with extension.
	 * It uses {@link Browser#getFilePath(String, String)}.
	 * If {@link Browser#isReported()} is <code>false</code>
	 * no file is saved.
	 * 
	 * @param content The string content to save.
	 * @param name The file name without extension.
	 * @param extension The file extension.
	 */
	default void saveFile(String content, String name, String extension) {
		getBrowser().saveFile(content, name, extension);
	}
	
	/**
	 * Saves bytes into named file with extension.
	 * It uses {@link Browser#getFilePath(String, String)}.
	 * If {@link Browser#isReported()} is <code>false</code>
	 * no file is saved.
	 * 
	 * @param bytes The bytes to save.
	 * @param name The file name without extension.
	 * @param extension The file extension.
	 */
	default void saveFile(byte[] bytes, String name, String extension) {
		getBrowser().saveFile(bytes, name, extension);
	}
	
	/**
	 * Saves file into named file with extension.
	 * It uses {@link Browser#getFilePath(String, String)}.
	 * If {@link Browser#isReported()} is <code>false</code>
	 * no file is saved.
	 * 
	 * @param file The file to save.
	 * @param name The file name without extension.
	 * @param extension The file extension.
	 */
	default void saveFile(File file, String name, String extension) {
		getBrowser().saveFile(file, name, extension);
	}
	
	/**
	 * Returns browser utilities instance.
	 * 
	 * @return The browser utilities.
	 */
	default BrowserUtils getUtils() {
		return getBrowser().getUtils();
	}
	
	/**
	 * Find all elements within the current context using the given mechanism.
	 * 
	 * @param by The locating mechanism to use
	 * @return A list of all {@link BrowserElement}s, or an empty list if nothing matches
	 */
	default List<BrowserElement> find(By by) {
		return getBrowser().find(this, by, BrowserElement.class);
	}
	
	/**
	 * Find all elements within the given context using the given mechanism.
	 * 
	 * @param context The browser context to search in
	 * @param by The locating mechanism to use
	 * @return A list of all {@link BrowserElement}s, or an empty list if nothing matches
	 */
	default List<BrowserElement> find(BrowserContext context, By by) {
		return getBrowser().find(context, by, BrowserElement.class);
	}
	
	/**
	 * Find all elements within the current context using the given mechanism
	 * as instances of the given {@link BrowserElement} or its subclass. 
	 * 
	 * @param by The locating mechanism to use
	 * @param elementCls The element class
	 * @return A list of all {@link BrowserElement}s as instances of the given 
	 * {@link BrowserElement} or its subclass, or an empty list if nothing matches
	 */
	default <T extends BrowserElement> List<T> find(By by, Class<T> elementCls) {
		return getBrowser().find(this, by, elementCls);
	}
	
	/**
	 * Find all elements within the given context using the given mechanism
	 * as instances of the given {@link BrowserElement} or its subclass. 
	 * 
	 * @param context The browser context to search in
	 * @param by The locating mechanism to use
	 * @param elementCls The element class
	 * @return A list of all {@link BrowserElement}s as instances of the given 
	 * {@link BrowserElement} or its subclass, or an empty list if nothing matches
	 */
	default <T extends BrowserElement> List<T> find(BrowserContext context, By by, Class<T> elementCls) {
		return getBrowser().find(context, by, elementCls);
	}

	/**
	 * Find the first element within the current context using the given mechanism.
	 * 
	 * @param by The locating mechanism
	 * @return The first matching element on the current context
	 * @throws NoSuchElementException If no matching elements are found
	 */
	default BrowserElement findOne(By by) {
		return getBrowser().findOne(this, by, BrowserElement.class, false);
	}

	/**
	 * Find the first element within the given context using the given mechanism.
	 * 
	 * @param context The browser context to search in
	 * @param by The locating mechanism
	 * @return The first matching element on the given context
	 * @throws NoSuchElementException If no matching elements are found
	 */
	default BrowserElement findOne(BrowserContext context, By by) {
		return getBrowser().findOne(context, by, BrowserElement.class, false);
	}
	
	/**
	 * Find the first element within the current context using the given mechanism
	 * as instance of the given {@link BrowserElement} or its subclass.
	 * 
	 * @param by The locating mechanism
	 * @param elementCls The element class
	 * @return The first matching element on the current context as instance of the given 
	 * {@link BrowserElement} or its subclass
	 * @throws NoSuchElementException If no matching elements are found
	 */
	default <T extends BrowserElement> T findOne(By by, Class<T> elementCls) {
		return getBrowser().findOne(this, by, elementCls, false);
	}
	
	/**
	 * Find the first element within the given context using the given mechanism
	 * as instance of the given {@link BrowserElement} or its subclass.
	 * 
	 * @param context The browser context to search in
	 * @param by The locating mechanism
	 * @param elementCls The element class
	 * @return The first matching element on the given context as instance of the given 
	 * {@link BrowserElement} or its subclass
	 * @throws NoSuchElementException If no matching elements are found
	 */
	default <T extends BrowserElement> T findOne(BrowserContext context, By by, Class<T> elementCls) {
		return getBrowser().findOne(context, by, elementCls, false);
	}
	
	/**
	 * Find the first element within the given context using the given mechanism
	 * as instance of the given {@link BrowserElement} or its subclass.
	 * If optional is <code>true</code> and no matching elements are found <code>null</code>
	 * is returned instead of throwing {@link NoSuchElementException}.
	 * 
	 * @param context The browser context to search in
	 * @param by The locating mechanism
	 * @param elementCls The element class
	 * @param optional <code>true</code> for returning <code>null</code> instead of throwing 
	 * {@link NoSuchElementException} if no matching elements are found
	 * @return The first matching element on the given context as instance of the given 
	 * {@link BrowserElement} or its subclass
	 * @throws NoSuchElementException If no matching elements are found and optional is <code>false</code>
	 */
	default <T extends BrowserElement> T findOne(BrowserContext context, By by, Class<T> elementCls, boolean optional) {
		return getBrowser().findOne(context, by, elementCls, optional);
	}
	
	/**
	 * Find the first element within the current context using the given mechanism.
	 * If no matching elements are found <code>null</code> is returned.
	 * 
	 * @param by The locating mechanism
	 * @return The first matching element on the current context or null 
	 * if no matching elements are found
	 */
	default BrowserElement findOneOptional(By by) {
		return getBrowser().findOne(this, by, BrowserElement.class, true);
	}
	
	/**
	 * Find the first element within the given context using the given mechanism.
	 * If no matching elements are found <code>null</code> is returned.
	 * 
	 * @param context The browser context to search in
	 * @param by The locating mechanism
	 * @return The first matching element on the current context or null 
	 * if no matching elements are found
	 */
	default BrowserElement findOneOptional(BrowserContext context, By by) {
		return getBrowser().findOne(context, by, BrowserElement.class, true);
	}
	
	/**
	 * Find the first element within the current context using the given mechanism
	 * as instance of the given {@link BrowserElement} or its subclass.
	 * If no matching elements are found <code>null</code> is returned.
	 * 
	 * @param by The locating mechanism
	 * @param elementCls The element class
	 * @return The first matching element on the current context or null 
	 * if no matching elements are found
	 */
	default <T extends BrowserElement> T findOneOptional(By by, Class<T> elementCls) {
		return getBrowser().findOne(this, by, elementCls, true);
	}
	
	/**
	 * Find the first element within the given context using the given mechanism
	 * as instance of the given {@link BrowserElement} or its subclass.
	 * If no matching elements are found <code>null</code> is returned.
	 * 
	 * @param context The browser context to search in
	 * @param by The locating mechanism
	 * @param elementCls The element class
	 * @return The first matching element on the current context or null 
	 * if no matching elements are found
	 */
	default <T extends BrowserElement> T findOneOptional(BrowserContext context, By by, Class<T> elementCls) {
		return getBrowser().findOne(context, by, elementCls, true);
	}
	
	/**
	 * Creates {@link ElementLocator} used to locate element.
	 * It uses {@link FindBy} and similar annotations assigned
	 * to the given field to create {@link By} locating mechanism.
	 * 
	 * @param field The field used as locator source
	 * @return The element locator
	 */
	default ElementLocator createElementLocator(Field field) {
		return getBrowser().createElementLocator(this, field);
	}
	
	/**
	 * Creates {@link ElementLocator} used to locate element.
	 * It uses the given {@link By} locating mechanism.
	 * 
	 * @param by The locating mechanism
	 * @return The element locator
	 */
	default ElementLocator createElementLocator(By by) {
		return getBrowser().createElementLocator(this, by);
	}
	
	/**
	 * Creates {@link ElementLocator} used to locate element.
	 * It uses the given {@link By} locating mechanism.
	 * Set lookupCached to <code>true</code> return  element 
	 * from cache on further calls.
	 * 
	 * @param by The locating mechanism
	 * @param lookupCached if lookup is cached
	 * @return The element locator
	 */
	default ElementLocator createElementLocator(By by, boolean lookupCached) {
		return getBrowser().createElementLocator(this, by, lookupCached);
	}
	
	@Override
	default WebDriver getWrappedDriver() {
		return getDriver();
	}
	
	@Override
	default Map<String, Object> getDataHolder() {
		return getBrowser().getDataHolder();
	}

	@Override
	default String getProperty(String key) {
		return getBrowser().getProperty(key);
	}
	
}
