package com.etnetera.qa.seleniumbrowser.module;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.context.VerificationException;
import com.etnetera.qa.seleniumbrowser.element.ElementFieldDecorator;
import com.etnetera.qa.seleniumbrowser.element.ElementManager;

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
		PageFactory.initElements(new ElementFieldDecorator(new DefaultElementLocatorFactory(this), this), this);
		afterInit();
		verify();
		afterVerification();
		return this;
	}
	
	public boolean isPresent() {
		return ElementManager.isPresent(element); 
	}
	
	public boolean isNotPresent() {
		return ElementManager.isNotPresent(element); 
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

	protected void afterInit() {
		// do whatever you want, i.e. bind another modules
	}
	
	protected void afterVerification() {
		// do whatever you want, i.e. some logic
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
