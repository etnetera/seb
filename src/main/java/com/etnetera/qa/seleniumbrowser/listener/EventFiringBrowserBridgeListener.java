/* Copyright 2016 Etnetera
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
package com.etnetera.qa.seleniumbrowser.listener;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterChangeValueOfEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterClickOnEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterFindByEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterNavigateBackEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterNavigateForwardEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterNavigateToEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterScriptEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeChangeValueOfEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeClickOnEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeFindByEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeNavigateBackEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeNavigateForwardEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeNavigateToEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeScriptEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnExceptionEvent;

public class EventFiringBrowserBridgeListener implements WebDriverEventListener {

	protected Browser browser;
	
	public EventFiringBrowserBridgeListener(Browser browser) {
		this.browser = browser;
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		triggerEvent(constructEvent(BeforeNavigateToEvent.class).with(url));
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		triggerEvent(constructEvent(AfterNavigateToEvent.class).with(url));
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		triggerEvent(constructEvent(BeforeNavigateBackEvent.class));
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		triggerEvent(constructEvent(AfterNavigateBackEvent.class));
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		triggerEvent(constructEvent(BeforeNavigateForwardEvent.class));
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		triggerEvent(constructEvent(AfterNavigateForwardEvent.class));
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		triggerEvent(constructEvent(BeforeFindByEvent.class).with(by, element));
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		triggerEvent(constructEvent(AfterFindByEvent.class).with(by, element));
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		triggerEvent(constructEvent(BeforeClickOnEvent.class).with(element));
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		triggerEvent(constructEvent(AfterClickOnEvent.class).with(element));
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		triggerEvent(constructEvent(BeforeChangeValueOfEvent.class).with(element));
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		triggerEvent(constructEvent(AfterChangeValueOfEvent.class).with(element));
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		triggerEvent(constructEvent(BeforeScriptEvent.class).with(script));
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		triggerEvent(constructEvent(AfterScriptEvent.class).with(script));
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		triggerEvent(constructEvent(OnExceptionEvent.class).with(throwable));
	}
	
	protected <T extends BrowserEvent> T constructEvent(Class<T> eventCls) {
		return browser.constructEvent(eventCls, browser);
	}
	
	protected void triggerEvent(BrowserEvent event) {
		browser.triggerEvent(event);
	}
	
}
