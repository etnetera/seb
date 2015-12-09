package com.etnetera.qa.seleniumbrowser.listener;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.browser.BrowserUtils;
import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterChangeValueOfEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterClickOnEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterFindByEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterModuleInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterNavigateBackEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterNavigateForwardEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterNavigateToEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterPageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterScriptEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeChangeValueOfEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeClickOnEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeFindByEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeModuleInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeNavigateBackEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeNavigateForwardEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeNavigateToEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforePageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeScriptEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnExceptionEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnModuleInitExceptionEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnPageInitExceptionEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
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
	public void onReport(OnReportEvent event) {}
	
	/**
	 * Called before {@link Page#init()}.
	 *
	 * @param event
	 */
	public void beforePageInit(BeforePageInitEvent event) {}
	
	/**
	 * Called whenever an exception would be thrown in {@link Page#init()}.
	 *
	 * @param event
	 */
	public void onPageInitException(OnPageInitExceptionEvent event) {}
	
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
	 * Called whenever an exception would be thrown in {@link Module#init()}.
	 *
	 * @param event
	 */
	public void onModuleInitException(OnModuleInitExceptionEvent event) {}
	
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
	
	/**
	 * Save string content into output file with given name and extension.
	 * 
	 * @param event
	 * @param content
	 * @param name
	 * @param extension
	 */
	protected void saveFile(BrowserEvent event, String content, String name, String extension) {
		event.saveFile(content, getListenerFileName(name), extension);
	}
	
	/**
	 * Save bytes into output file with given name and extension.
	 * 
	 * @param event
	 * @param bytes
	 * @param name
	 * @param extension
	 */
	protected void saveFile(BrowserEvent event, byte[] bytes, String name, String extension) {
		event.saveFile(bytes, getListenerFileName(name), extension);
	}
	
	/**
	 * Save file into output file with given name and extension.
	 * 
	 * @param event
	 * @param file
	 * @param name
	 * @param extension
	 */
	protected void saveFile(BrowserEvent event, File file, String name, String extension) {
		event.saveFile(file, getListenerFileName(name), extension);
	}
	
	protected String getListenerFileName(String name) {
		return BrowserUtils.join("-", label, name);
	}
	
	protected String generateLabel() {
		String name = getClass().getSimpleName();
		String endTrim = "Listener";
		return name.endsWith(endTrim) ? name.substring(0, name.length() - endTrim.length()) : name;
	}

}
