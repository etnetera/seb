package com.etnetera.qa.seleniumbrowser.listener;

import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterChangeValueOfEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterClickOnEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterFindByEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterNavigateBackEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterNavigateForwardEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterNavigateToEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.AfterScriptEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeChangeValueOfEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeClickOnEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeFindByEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeNavigateBackEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeNavigateForwardEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeNavigateToEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BeforeScriptEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.listener.event.OnExceptionEvent;

public class EventFiringBrowserBridgeListener implements WebDriverEventListener {

	protected Browser browser;
	
	public EventFiringBrowserBridgeListener(Browser browser) {
		this.browser = browser;
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		BeforeNavigateToEvent event = constructEvent(BeforeNavigateToEvent.class);
		event.setUrl(url);
		triggerEvent(event, l -> l.beforeNavigateTo(event));
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		AfterNavigateToEvent event = constructEvent(AfterNavigateToEvent.class);
		event.setUrl(url);
		triggerEvent(event, l -> l.afterNavigateTo(event));
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		BeforeNavigateBackEvent event = constructEvent(BeforeNavigateBackEvent.class);
		triggerEvent(event, l -> l.beforeNavigateBack(event));
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		AfterNavigateBackEvent event = constructEvent(AfterNavigateBackEvent.class);
		triggerEvent(event, l -> l.afterNavigateBack(event));
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		BeforeNavigateForwardEvent event = constructEvent(BeforeNavigateForwardEvent.class);
		triggerEvent(event, l -> l.beforeNavigateForward(event));
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		AfterNavigateForwardEvent event = constructEvent(AfterNavigateForwardEvent.class);
		triggerEvent(event, l -> l.afterNavigateForward(event));
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		BeforeFindByEvent event = constructEvent(BeforeFindByEvent.class);
		event.setBy(by);
		event.setElement(element);
		triggerEvent(event, l -> l.beforeFindBy(event));
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		AfterFindByEvent event = constructEvent(AfterFindByEvent.class);
		event.setBy(by);
		event.setElement(element);
		triggerEvent(event, l -> l.afterFindBy(event));
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		BeforeClickOnEvent event = constructEvent(BeforeClickOnEvent.class);
		event.setElement(element);
		triggerEvent(event, l -> l.beforeClickOn(event));
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		AfterClickOnEvent event = constructEvent(AfterClickOnEvent.class);
		event.setElement(element);
		triggerEvent(event, l -> l.afterClickOn(event));
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		BeforeChangeValueOfEvent event = constructEvent(BeforeChangeValueOfEvent.class);
		event.setElement(element);
		triggerEvent(event, l -> l.beforeChangeValueOf(event));
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		AfterChangeValueOfEvent event = constructEvent(AfterChangeValueOfEvent.class);
		event.setElement(element);
		triggerEvent(event, l -> l.afterChangeValueOf(event));
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		BeforeScriptEvent event = constructEvent(BeforeScriptEvent.class);
		event.setScript(script);
		triggerEvent(event, l -> l.beforeScript(event));
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		AfterScriptEvent event = constructEvent(AfterScriptEvent.class);
		event.setScript(script);
		triggerEvent(event, l -> l.afterScript(event));
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		OnExceptionEvent event = constructEvent(OnExceptionEvent.class);
		event.setThrowable(throwable);
		triggerEvent(event, l -> l.onException(event));
	}
	
	protected <T extends BrowserEvent> T constructEvent(Class<T> eventCls) {
		return browser.constructEvent(eventCls, browser);
	}
	
	protected void triggerEvent(BrowserEvent event, Consumer<BrowserListener> consumer) {
		browser.triggerEvent(event, consumer);
	}
	
}
