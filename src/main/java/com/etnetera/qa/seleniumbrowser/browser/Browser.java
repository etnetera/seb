package com.etnetera.qa.seleniumbrowser.browser;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.page.Page;
import com.etnetera.qa.seleniumbrowser.page.PageManager;

public class Browser implements BrowserContext {
	
	protected BrowserConfiguration configuration;
	
	protected WebDriver driver;
	
	protected String baseUrl;
	
	protected String baseUrlRegex;
	
	protected boolean urlVerification;
	
	protected double waitTimeout;
	
	protected double waitRetryInterval;
	
	protected Page page;

	public Browser(BrowserConfiguration configuration) {
		this.configuration = configuration;
		this.driver = configuration.getDriver();
		this.baseUrl = configuration.getBaseUrl();
		this.baseUrlRegex = configuration.getBaseUrlRegex();
		this.urlVerification = configuration.isUrlVerification();
		this.waitTimeout = configuration.getWaitTimeout();
		this.waitRetryInterval = configuration.getWaitRetryInterval();
	}

	public BrowserConfiguration getConfiguration() {
		return configuration;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BrowserConfiguration> T getConfiguration(Class<T> configuration) {
		return (T) getConfiguration();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseUrlRegex() {
		return baseUrlRegex;
	}

	public void setBaseUrlRegex(String baseUrlRegex) {
		this.baseUrlRegex = baseUrlRegex;
	}

	public boolean isUrlVerification() {
		return urlVerification;
	}

	public void setUrlVerification(boolean urlVerification) {
		this.urlVerification = urlVerification;
	}

	@Override
	public double getWaitTimeout() {
		return waitTimeout;
	}

	public void setWaitTimeout(double waitTimeout) {
		this.waitTimeout = waitTimeout;
	}

	@Override
	public double getWaitRetryInterval() {
		return waitRetryInterval;
	}

	public void setWaitRetryInterval(double waitRetryInterval) {
		this.waitRetryInterval = waitRetryInterval;
	}
	
	@Override
	public BrowserContext getContext() {
		return this;
	}
	
	@Override
	public Browser getBrowser() {
		return this;
	}

	@Override
	public Page getPage() {
		return page;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Object> T getPage(Class<T> page) {
		return (T) this.page;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Object> T getPage(T page) {
		return (T) this.page;
	}

	@Override
	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public boolean isAt(Class<?> page) {
		return this.page != null && page.isAssignableFrom(this.page.getClass());
	}
	
	@Override
	public boolean isAt(Object page) {
		return this.page != null && page.getClass().isAssignableFrom(this.page.getClass());
	}
	
	@Override
	public boolean verifyAt(Class<? extends Page> page) {
		return PageManager.verifyAt(page, this);
	}
	
	@Override
	public boolean verifyAt(Page page) {
		return PageManager.verifyAt(page);
	}
	
	@Override
	public <T extends Page> T goTo(Class<T> page) {
		return PageManager.goTo(page, this);
	}
	
	@Override
	public <T extends Page> T goTo(T page) {
		return PageManager.goTo(page);
	}
	
	@Override
	public <T extends Page> T initPage(Class<T> page) {
		return PageManager.init(page, this);
	}
	
	@Override
	public <T extends Page> T initPage(T page) {
		return PageManager.init(page);
	}
	
	@Override
	public Page initOnePage(Object firstPage, Object ... anotherPages) {
		return PageManager.initOne(this, firstPage, anotherPages);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return driver.findElement(by);
	}
	
	public void quit() {
		if (driver != null) driver.quit();
	}
	
}
