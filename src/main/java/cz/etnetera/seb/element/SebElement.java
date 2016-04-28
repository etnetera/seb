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
package cz.etnetera.seb.element;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.SebContext;
import cz.etnetera.seb.SebException;

/**
 * Wraps {@link WebElement} and holds {@link SebContext}
 * with {@link WebDriver}. Allows to check if element is present
 * or not and can be optional. Subclasses can override {@link SebElement#initPresent()}
 * method to add some specific behavior.
 */
public class SebElement implements SebContext, WebElement, WrapsElement, WrapsDriver, Locatable {
	
	protected SebContext context;
	
	protected WebElement webElement;
	
	protected boolean optional;
	
	protected boolean present;
	
	protected boolean initiated;
	
	protected boolean initiating;
	
	public SebElement with(SebContext context, WebElement webElement, boolean optional) {
		this.context = context;
		this.webElement = webElement;
		this.optional = optional;
		return this;
	}
	
	public WebElement getWebElement() {
		if (!present)
			tryInitPresent(isPresent(webElement));
		return webElement;
	}

	public boolean isOptional() {
		return optional;
	}
	
	public boolean isInitiated() {
		return initiated;
	}

	@Override
	public SebContext getContext() {
		return context;
	}

	public SebElement init() {
		if (!initiating) {
			initiating = true;
			try {
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
			} finally {
				initiated = true;
				initiating = false;
			}
		}
		return this;
	}
	
	protected void tryInitPresent(boolean justPresent) {
		if (!present && justPresent) {
			present = true;
			initPresent();
		}
	}
	
	/**
	 * Called when {@link SebElement} is initiated
	 * and is present or before getting wrapped {@link WebElement}
	 * which was not present and is present now.
	 */
	protected void initPresent() {
		// do what you need
	}
	
	public void checkIfPresent() throws NoSuchElementException {
		getContext().checkIfPresent(webElement);
		tryInitPresent(true);
	}
	
	public boolean isPresent() {
		boolean justPresent = getContext().isPresent(webElement);
		tryInitPresent(justPresent);
		return justPresent;
	}
	
	public boolean isNotPresent() {
		boolean justNotPresent = getContext().isNotPresent(webElement);
		tryInitPresent(!justNotPresent);
		return justNotPresent;
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
	public Rectangle getRect() {
		return getWebElement().getRect();
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
	public Seb getSeb() {
		return getContext().getSeb();
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
	 * It throws {@link SebException} if driver is not 
	 * implementing {@link JavascriptExecutor}.
	 */
	public void blur() {
		if (getDriver() instanceof JavascriptExecutor)
			getSeb().getJavascriptLibrary().callEmbeddedSelenium(getDriver(), "triggerEvent", this, "blur");
		else
			throw new SebException("Triggering blur event is supported with JavascriptExecutor driver only, this is " + getDriver().getClass());
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
	
	/**
     * Returns true if at least one of the context elements matches the tag.
     * 
     * @param tag The tag to match
     * @return true if at least one of the context elements matches the tag
     */
    public boolean is(String tag) {
    	return getWebElement().getTagName().equalsIgnoreCase(tag);
    }

}
