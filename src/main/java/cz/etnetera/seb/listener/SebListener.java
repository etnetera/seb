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
package cz.etnetera.seb.listener;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.SebContext;
import cz.etnetera.seb.configuration.SebConfiguration;
import cz.etnetera.seb.event.SebEvent;
import cz.etnetera.seb.event.impl.AfterSebQuitEvent;
import cz.etnetera.seb.event.impl.AfterChangeValueOfEvent;
import cz.etnetera.seb.event.impl.AfterClickOnEvent;
import cz.etnetera.seb.event.impl.AfterFindByEvent;
import cz.etnetera.seb.event.impl.AfterModuleInitEvent;
import cz.etnetera.seb.event.impl.AfterNavigateBackEvent;
import cz.etnetera.seb.event.impl.AfterNavigateForwardEvent;
import cz.etnetera.seb.event.impl.AfterNavigateToEvent;
import cz.etnetera.seb.event.impl.AfterPageInitEvent;
import cz.etnetera.seb.event.impl.AfterScriptEvent;
import cz.etnetera.seb.event.impl.BeforeSebQuitEvent;
import cz.etnetera.seb.event.impl.LogEvent;
import cz.etnetera.seb.event.impl.BeforeChangeValueOfEvent;
import cz.etnetera.seb.event.impl.BeforeClickOnEvent;
import cz.etnetera.seb.event.impl.BeforeDriverConstructEvent;
import cz.etnetera.seb.event.impl.BeforeFindByEvent;
import cz.etnetera.seb.event.impl.BeforeModuleInitEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateBackEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateForwardEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateToEvent;
import cz.etnetera.seb.event.impl.BeforePageInitEvent;
import cz.etnetera.seb.event.impl.BeforeScriptEvent;
import cz.etnetera.seb.event.impl.OnExceptionEvent;
import cz.etnetera.seb.event.impl.OnFileSaveEvent;
import cz.etnetera.seb.event.impl.OnModuleInitExceptionEvent;
import cz.etnetera.seb.event.impl.OnPageInitExceptionEvent;
import cz.etnetera.seb.event.impl.OnReportEvent;
import cz.etnetera.seb.module.Module;
import cz.etnetera.seb.page.Page;

public class SebListener {

	protected Seb seb;
	
	protected String label;
	
	protected Set<Class<? extends SebEvent>> enabledEvents;
	
	protected Set<Class<? extends SebEvent>> disabledEvents;
	
	/**
	 * Initialize listener.
	 * Is called from Seb directly
	 * and should not be called manually.
	 */
	public void init(Seb seb) {
		this.seb = seb;
		label = generateLabel();
	}
	
	public boolean isEnabled(SebEvent event) {
		return isEnabled(event.getClass());
	}
	
	public boolean isEnabled(Class<? extends SebEvent> event) {
		if (enabledEvents != null)
			return enabledEvents.contains(event);
		if (disabledEvents != null)
			return !disabledEvents.contains(event);
		return true;
	}
	
	/**
	 * Enables specific events only.
	 * It overrides all disabled events.
	 * 
	 * @param events Events to enable
	 * @return This instance
	 */
	@SuppressWarnings("unchecked")
	public SebListener enable(Class<? extends SebEvent>... events) {
		if (events != null) {
			disabledEvents = null;
			if (enabledEvents == null)
				enabledEvents = new HashSet<>();
			for (Class<? extends SebEvent> event : events) {
				enabledEvents.add(event);
			}
		}
		return this;
	}
	
	/**
	 * Disables specific events only.
	 * It overrides all enabled events.
	 * 
	 * @param events Events to enable
	 * @return This instance
	 */
	@SuppressWarnings("unchecked")
	public SebListener disable(Class<? extends SebEvent>... events) {
		if (events != null) {
			enabledEvents = null;
			if (disabledEvents == null)
				disabledEvents = new HashSet<>();
			for (Class<? extends SebEvent> event : events) {
				disabledEvents.add(event);
			}
		}
		return this;
	}
	
	/**
	 * Called on {@link SebContext#triggerEvent(SebEvent)}.
	 * This is basic handler implementation which
	 * is called for every triggered event.
	 * Some events can have its own handling method
	 * like {@link SebListener#onReport(OnReportEvent)}. 
	 *
	 * @param event
	 */
	public void handle(SebEvent event) {}
	
	/**
	 * Called on {@link SebContext#report(String label)}.
	 *
	 * @param event
	 */
	public void onReport(OnReportEvent event) {}
	
	/**
	 * Called on {@link SebContext#saveFile(File, String, String)}
	 * and similar methods.
	 *
	 * @param event
	 */
	public void onFileSave(OnFileSaveEvent event) {}
	
	/**
	 * Called before {@link SebConfiguration#getDriver(org.openqa.selenium.remote.DesiredCapabilities)}.
	 *
	 * @param event
	 */
	public void beforeDriverConstruct(BeforeDriverConstructEvent event) {}
	
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
	 * Called before {@link Seb#quit()}.
	 *
	 * @param event
	 */
	public void beforeSebQuit(BeforeSebQuitEvent event) {}
	
	/**
	 * Called after {@link Seb#quit()}.
	 *
	 * @param event
	 */
	public void afterSebQuit(AfterSebQuitEvent event) {}
	
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
	 * Called whenever a log event is triggered.
	 *
	 * @param event
	 */
	public void log(LogEvent event) {}
	
	/**
	 * Save string content into output file with given name and extension.
	 * 
	 * @param event
	 * @param content
	 * @param name
	 * @param extension
	 */
	protected void saveFile(SebEvent event, String content, String name, String extension) {
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
	protected void saveFile(SebEvent event, byte[] bytes, String name, String extension) {
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
	protected void saveFile(SebEvent event, File file, String name, String extension) {
		event.saveFile(file, getListenerFileName(name), extension);
	}
	
	protected String getListenerFileName(String name) {
		return seb.getUtils().join(Seb.LABEL_DELIMITER, label, name);
	}
	
	protected String generateLabel() {
		String name = getClass().getSimpleName();
		String endTrim = "Listener";
		return name.endsWith(endTrim) ? name.substring(0, name.length() - endTrim.length()) : name;
	}

}
