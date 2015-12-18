package com.etnetera.qa.seleniumbrowser.logic;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;

/**
 * Basic logic.
 */
abstract public class Logic implements BrowserContext {
	
	protected BrowserContext context;

	public Logic with(BrowserContext context) {
		this.context = context;
		return this;
	}
	
	@Override
	public BrowserContext getContext() {
		return context;
	}
	
	@Override
	public Browser getBrowser() {
		return context.getBrowser();
	}
	
	public Logic init() {
		// do whatever you want
		return this;
	}

	@Override
	public List<WebElement> findElements(By by) {
		return context.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return context.findElement(by);
	}
	
}
