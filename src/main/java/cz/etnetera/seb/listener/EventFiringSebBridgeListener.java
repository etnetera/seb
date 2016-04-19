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

import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.event.SebEvent;
import cz.etnetera.seb.event.impl.AfterChangeValueOfEvent;
import cz.etnetera.seb.event.impl.AfterClickOnEvent;
import cz.etnetera.seb.event.impl.AfterFindByEvent;
import cz.etnetera.seb.event.impl.AfterNavigateBackEvent;
import cz.etnetera.seb.event.impl.AfterNavigateForwardEvent;
import cz.etnetera.seb.event.impl.AfterNavigateRefreshEvent;
import cz.etnetera.seb.event.impl.AfterNavigateToEvent;
import cz.etnetera.seb.event.impl.AfterScriptEvent;
import cz.etnetera.seb.event.impl.BeforeChangeValueOfEvent;
import cz.etnetera.seb.event.impl.BeforeClickOnEvent;
import cz.etnetera.seb.event.impl.BeforeFindByEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateBackEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateForwardEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateRefreshEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateToEvent;
import cz.etnetera.seb.event.impl.BeforeScriptEvent;
import cz.etnetera.seb.event.impl.LogEvent;

public class EventFiringSebBridgeListener implements WebDriverEventListener {

	protected Seb seb;
	
	public EventFiringSebBridgeListener(Seb seb) {
		this.seb = seb;
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
	public void beforeNavigateRefresh(WebDriver driver) {
		triggerEvent(constructEvent(BeforeNavigateRefreshEvent.class));
	}
	
	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		triggerEvent(constructEvent(AfterNavigateRefreshEvent.class));
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
		triggerEvent(constructEvent(LogEvent.class).with(Level.INFO, "Webdriver exception", throwable));
	}
	
	protected <T extends SebEvent> T constructEvent(Class<T> eventCls) {
		return seb.constructEvent(eventCls, seb);
	}
	
	protected void triggerEvent(SebEvent event) {
		seb.triggerEvent(event);
	}
	
}
