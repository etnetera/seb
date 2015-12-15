package com.etnetera.qa.seleniumbrowser.module;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.context.VerificationException;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterModuleInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeModuleInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnModuleInitExceptionEvent;

/**
 * Basic module which supports elements and modules auto loading.
 * 
 * @author zdenek
 */
abstract public class Module implements BrowserContext, WebElement {
	
	protected BrowserContext context;
	
	protected WebElement element;
	
	public Module with(BrowserContext context, WebElement element) {
		this.context = context;
		this.element = element;
		return this;
	}
	
	public WebElement getElement() {
		return element;
	}

	public Module init() {
		// initialize with present element only
		if (isNotPresent())
			return this;
		try {
			triggerEvent(constructEvent(BeforeModuleInitEvent.class).with(this));
			beforeInit();
			beforeInitElements();
			initElements();
			afterInitElements();
			beforeVerify();
			verify();
			afterVerify();
			afterInit();
		} catch (Exception e) {
			triggerEvent(constructEvent(OnModuleInitExceptionEvent.class).with(this, e));
			throw e;
		}
		triggerEvent(constructEvent(AfterModuleInitEvent.class).with(this));
		return this;
	}
	
	public boolean isPresent() {
		return isPresent(element); 
	}
	
	public boolean isNotPresent() {
		return isNotPresent(element); 
	}
	
	public void verify() throws VerificationException {
		try {
			verifyThis();
		} catch (Exception e) {
			throw new VerificationException("Module is wrong " + this.getClass().getName(), e);
		}
	}
	
	protected void verifyThis() {
		// check if we are in right module
	}

	protected void beforeInit() {
		// do whatever you want
	}
	
	protected void beforeInitElements() {
		// do whatever you want
	}
	
	protected void afterInitElements() {
		// do whatever you want
	}
	
	protected void beforeVerify() {
		// do whatever you want
	}
	
	protected void afterVerify() {
		// do whatever you want
	}
	
	protected void afterInit() {
		// do whatever you want
	}
	
	@Override
	public BrowserContext getContext() {
		return context;
	}
	
	@Override
	public Browser getBrowser() {
		return context.getBrowser();
	}
	
	@Override
	public double getWaitTimeout() {
		return context.getWaitTimeout();
	}

	@Override
	public double getWaitRetryInterval() {
		return context.getWaitRetryInterval();
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		return element.getScreenshotAs(target);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return element.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return element.findElement(by);
	}

	@Override
	public void click() {
		element.click();
	}

	@Override
	public void submit() {
		element.submit();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		element.sendKeys(keysToSend);
	}

	@Override
	public void clear() {
		element.clear();
	}

	@Override
	public String getTagName() {
		return element.getTagName();
	}

	@Override
	public String getAttribute(String name) {
		return element.getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		return element.isSelected();
	}

	@Override
	public boolean isEnabled() {
		return element.isEnabled();
	}

	@Override
	public String getText() {
		return element.getText();
	}

	@Override
	public boolean isDisplayed() {
		return element.isDisplayed();
	}

	@Override
	public Point getLocation() {
		return element.getLocation();
	}

	@Override
	public Dimension getSize() {
		return element.getSize();
	}

	@Override
	public String getCssValue(String propertyName) {
		return element.getCssValue(propertyName);
	}
	
}
