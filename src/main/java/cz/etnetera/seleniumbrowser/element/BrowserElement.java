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
package cz.etnetera.seleniumbrowser.element;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

import cz.etnetera.seleniumbrowser.browser.Browser;
import cz.etnetera.seleniumbrowser.browser.BrowserContext;
import cz.etnetera.seleniumbrowser.browser.BrowserException;

/**
 * Wraps {@link WebElement} and holds {@link BrowserContext}
 * with {@link WebDriver}. Allows to check if element is present
 * or not and can be optional. Subclasses can override {@link BrowserElement#initPresent()}
 * method to add some specific behavior.
 */
public class BrowserElement implements BrowserContext, WebElement, WrapsElement, WrapsDriver, Locatable {
	
	protected BrowserContext context;
	
	protected WebElement webElement;
	
	protected boolean optional;
	
	protected boolean present;
	
	public BrowserElement with(BrowserContext context, WebElement webElement, boolean optional) {
		this.context = context;
		this.webElement = webElement;
		this.optional = optional;
		return this;
	}
	
	public WebElement getWebElement() {
		if (!present && isPresent(webElement)) {
			present = true;
			initPresent();
		}
		return webElement;
	}

	public boolean isOptional() {
		return optional;
	}
	
	@Override
	public BrowserContext getContext() {
		return context;
	}

	final public BrowserElement init() {
		try {
			checkIfPresent(webElement);
			present = true;
		} catch (NoSuchElementException e) {
			if (!optional)
				throw e;
		}
		if (present)
			initPresent();
		return this;
	}
	
	/**
	 * Called when {@link BrowserElement} is initiated
	 * and is present or before getting wrapped {@link WebElement}
	 * which was not present and is present now.
	 */
	protected void initPresent() {
		// do what you need
	}
	
	public void checkIfPresent() throws NoSuchElementException {
		getContext().checkIfPresent(getWebElement());
	}
	
	public boolean isPresent() {
		return getContext().isPresent(getWebElement());
	}
	
	public boolean isNotPresent() {
		return getContext().isNotPresent(getWebElement());
	}
	
	@Override
	public List<WebElement> findElements(By by) {
		return getWebElement().findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return getWebElement().findElement(by);
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		return getWebElement().getScreenshotAs(target);
	}

	@Override
	public void click() {
		getWebElement().click();
	}

	@Override
	public void submit() {
		getWebElement().submit();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		getWebElement().sendKeys(keysToSend);
	}

	@Override
	public void clear() {
		getWebElement().clear();
	}

	@Override
	public String getTagName() {
		return getWebElement().getTagName();
	}

	@Override
	public String getAttribute(String name) {
		return getWebElement().getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		return getWebElement().isSelected();
	}

	@Override
	public boolean isEnabled() {
		return getWebElement().isEnabled();
	}

	@Override
	public String getText() {
		return getWebElement().getText();
	}

	@Override
	public boolean isDisplayed() {
		return getWebElement().isDisplayed();
	}

	@Override
	public Point getLocation() {
		return getWebElement().getLocation();
	}

	@Override
	public Dimension getSize() {
		return getWebElement().getSize();
	}

	@Override
	public String getCssValue(String propertyName) {
		return getWebElement().getCssValue(propertyName);
	}
	
	@Override
	public WebElement getWrappedElement() {
		return getWebElement();
	}
	
	@Override
	public Coordinates getCoordinates() {
		// Not checked cast of element, but we knows that every element
		// is created using with locatable interface.
		return ((Locatable) getWebElement()).getCoordinates();
	}
	
	@Override
	public Browser getBrowser() {
		return getContext().getBrowser();
	}
	
	@Override
	public double getWaitTimeout() {
		return getContext().getWaitTimeout();
	}

	@Override
	public double getWaitRetryInterval() {
		return getContext().getWaitRetryInterval();
	}
	
	/**
	 * Loose focus from element.
	 * It works only for {@link JavascriptExecutor} drivers.
	 * It throws {@link BrowserException} if driver is not 
	 * implementing {@link JavascriptExecutor}.
	 */
	public void blur() {
		if (getDriver() instanceof JavascriptExecutor)
			getBrowser().getJavascriptLibrary().callEmbeddedSelenium(getDriver(), "triggerEvent", this, "blur");
		else
			throw new BrowserException("Triggering blur event is supported with JavascriptExecutor driver only, this is " + getDriver().getClass());
	}
	
	/**
	 * Returns true if element has the given class.
	 * 
     * @param className class to check for
     * @return true if element has the given class
	 */
	public boolean hasClass(String className) {
		return getClasses().contains(className);
	}
	
	/**
     * Returns the class names present on element. 
     * The result is a unique set and is in alphabetical order.
     * 
     * @return the class names present on element.
     */
	public List<String> getClasses() {
    	String classAttr = getAttribute("class");
    	return Stream.of((classAttr == null ? "" : classAttr).trim().split("\\s+")).distinct().sorted().collect(Collectors.toList());
    }

}
