package com.etnetera.qa.seleniumbrowser.browser;

import java.io.File;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.Sleeper;

import com.etnetera.qa.seleniumbrowser.element.ElementManager;
import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.page.Page;

public interface BrowserContext extends SearchContext {

	public BrowserContext getContext();
	
	public Browser getBrowser();
	
	@SuppressWarnings("unchecked")
	public default <T> T getBrowser(Class<T> browser) {
		return (T) getBrowser();
	}
	
	public default WebDriver getDriver() {
		return getBrowser().getDriver();
	}
	
	@SuppressWarnings("unchecked")
	public default <T> T getDriver(Class<T> driver) {
		return (T) getDriver();
	}
	
	public default void getUrl(String url) {
		getBrowser().getUrl(url);
	}
	
	public default double getWaitTimeout() {
		return getBrowser().getWaitTimeout();
	}
	
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
	
	public default Page getPage() {
		return getBrowser().getPage();
	}
	
	public default <T extends Object> T getPage(Class<T> page) {
		return getBrowser().getPage(page);
	}
	
	public default <T extends Object> T getPage(T page) {
		return getBrowser().getPage(page);
	}

	public default void setPage(Page page) {
		getBrowser().setPage(page);
	}

	/**
	 * Check if actual page is a given class.
	 * 
	 * @param page
	 * @return
	 */
	public default boolean isAt(Class<?> page) {
		return getBrowser().isAt(page);
	}
	
	/**
	 * Check if actual page is a given object.
	 * 
	 * @param page
	 * @return
	 */
	public default boolean isAt(Object page) {
		return getBrowser().isAt(page);
	}
	
	/**
	 * Check if browser is at given page class.
	 * 
	 * @param page
	 * @return
	 */
	public default boolean verifyAt(Class<? extends Page> page) {
		return getBrowser().verifyAt(page);
	}
	
	/**
	 * Check if browser is at given page.
	 * 
	 * @param page
	 * @return
	 */
	public default boolean verifyAt(Page page) {
		return getBrowser().verifyAt(page);
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
	
	public default Page initOnePage(Object firstPage, Object ... anotherPages) {
		return getBrowser().initOnePage(firstPage, anotherPages);
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
	
}
