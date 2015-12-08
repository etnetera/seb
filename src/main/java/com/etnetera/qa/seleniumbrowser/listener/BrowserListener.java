package com.etnetera.qa.seleniumbrowser.listener;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterChangeValueOfEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterClickOnEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterFindByEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterModuleInitEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterNavigateBackEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterNavigateForwardEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterNavigateToEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterPageInitEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterScriptEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeChangeValueOfEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeClickOnEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeFindByEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeModuleInitEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeNavigateBackEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeNavigateForwardEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeNavigateToEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforePageInitEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeScriptEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.OnExceptionEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.ReportEvent;
import com.etnetera.qa.seleniumbrowser.module.Module;
import com.etnetera.qa.seleniumbrowser.page.Page;

public class BrowserListener {

	protected String label;
	
	public void init() {
		label = generateLabel();
	}
	
	/**
	 * Called on {@link BrowserContext#report(String label)}.
	 *
	 * @param event
	 */
	public void report(ReportEvent event) {}
	
	/**
	 * Called before {@link Page#init()}.
	 *
	 * @param event
	 */
	public void beforePageInit(BeforePageInitEvent event) {}
	
	/**
	 * Called after {@link Page#init()}.
	 *
	 * @param event
	 */
	public void afterPageInit(AfterPageInitEvent event) {}
	
	/**
	 * Called before {@link Module#init()}.
	 *
	 * @param event
	 */
	public void beforeModuleInit(BeforeModuleInitEvent event) {}
	
	/**
	 * Called after {@link Module#init()}.
	 *
	 * @param event
	 */
	public void afterModuleInit(AfterModuleInitEvent event) {}
	
	/**
	 * Called before {@link Browser#quit()}.
	 *
	 * @param event
	 */
	public void beforeBrowserQuit(BeforeBrowserQuitEvent event) {}
	
	/**
	 * Called after {@link Browser#quit()}.
	 *
	 * @param event
	 */
	public void afterBrowserQuit(AfterBrowserQuitEvent event) {}
	
	/**
	 * Called before {@link org.openqa.selenium.WebDriver#get get(String url)}
	 * respectively {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(String url)}.
	 *
	 * @param event
	 */
	public void beforeNavigateTo(BeforeNavigateToEvent event) {}

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#get get(String url)}
	 * respectively {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(String url)}. Not called, if an exception is thrown.
	 *
	 * @param event
	 */
	public void afterNavigateTo(AfterNavigateToEvent event) {}

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#back
	 * navigate().back()}.
	 *
	 * @param event
	 */
	public void beforeNavigateBack(BeforeNavigateBackEvent event) {}

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation
	 * navigate().back()}. Not called, if an exception is thrown.
	 *
	 * @param event
	 */
	public void afterNavigateBack(AfterNavigateBackEvent event) {}

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#forward
	 * navigate().forward()}.
	 *
	 * @param event
	 */
	public void beforeNavigateForward(BeforeNavigateForwardEvent event) {}

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#forward
	 * navigate().forward()}. Not called, if an exception is thrown.
	 *
	 * @param event
	 */
	public void afterNavigateForward(AfterNavigateForwardEvent event) {}

	/**
	 * Called before {@link WebDriver#findElement WebDriver.findElement(...)},
	 * or {@link WebDriver#findElements WebDriver.findElements(...)}, or
	 * {@link WebElement#findElement WebElement.findElement(...)}, or
	 * {@link WebElement#findElement WebElement.findElements(...)}.
	 *
	 * @param event
	 */
	public void beforeFindBy(BeforeFindByEvent event) {}

	/**
	 * Called after {@link WebDriver#findElement WebDriver.findElement(...)}, or
	 * {@link WebDriver#findElements WebDriver.findElements(...)}, or
	 * {@link WebElement#findElement WebElement.findElement(...)}, or
	 * {@link WebElement#findElement WebElement.findElements(...)}.
	 *
	 * @param event
	 */
	public void afterFindBy(AfterFindByEvent event) {}

	/**
	 * Called before {@link WebElement#click WebElement.click()}.
	 *
	 * @param event
	 */
	public void beforeClickOn(BeforeClickOnEvent event) {}

	/**
	 * Called after {@link WebElement#click WebElement.click()}. Not called, if
	 * an exception is thrown.
	 *
	 * @param event
	 */
	public void afterClickOn(AfterClickOnEvent event) {}

	/**
	 * Called before {@link WebElement#clear WebElement.clear()},
	 * {@link WebElement#sendKeys WebElement.sendKeys(...)}.
	 *
	 * @param event
	 */
	public void beforeChangeValueOf(BeforeChangeValueOfEvent event) {}

	/**
	 * Called after {@link WebElement#clear WebElement.clear()},
	 * {@link WebElement#sendKeys WebElement.sendKeys(...)}}. Not called, if an
	 * exception is thrown.
	 *
	 * @param event
	 */
	public void afterChangeValueOf(AfterChangeValueOfEvent event) {}

	/**
	 * Called before
	 * {@link org.openqa.selenium.remote.RemoteWebDriver#executeScript(java.lang.String, java.lang.Object[]) }
	 *
	 * @param event
	 */
	// Previously: Called before {@link WebDriver#executeScript(String)}
	// See the same issue below.
	public void beforeScript(BeforeScriptEvent event) {}

	/**
	 * Called after
	 * {@link org.openqa.selenium.remote.RemoteWebDriver#executeScript(java.lang.String, java.lang.Object[]) }
	 * . Not called if an exception is thrown
	 *
	 * @param event
	 */
	// Previously: Called after {@link WebDriver#executeScript(String)}. Not
	// called if an exception is thrown
	// So someone should check if this is right. There is no executeScript
	// method
	// in WebDriver, but there is in several other places, like this one
	public void afterScript(AfterScriptEvent event) {}

	/**
	 * Called whenever an exception would be thrown.
	 *
	 * @param event
	 */
	public void onException(OnExceptionEvent event) {}
	
	protected String generateLabel() {
		return getClass().getSimpleName();
	}

}
