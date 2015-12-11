package com.etnetera.qa.seleniumbrowser.event;

import java.io.File;
import java.time.LocalDateTime;

import org.openqa.selenium.WebDriver;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.browser.BrowserUtils;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

abstract public class BrowserEvent {
	
	protected BrowserContext context;
	
	protected LocalDateTime time;
	
	protected String browserLabel;
	
	protected String label;
	
	protected String filePrefix;
	
	public BrowserEvent with(BrowserContext context, LocalDateTime time) {
		this.context = context;
		this.time = time;
		return this;
	}

	abstract public void notify(BrowserListener listener);
	
	public void notifyEnabled(BrowserListener listener) {
		if (listener.isEnabled(this)) {
			notify(listener);
		}
	}
	
	/**
	 * Is called before event is triggered.
	 */
	public void init() {
		browserLabel = context.getBrowser().getLabel();
		label = generateLabel();
		filePrefix = BrowserUtils.join(Browser.LABEL_DELIMITER, time.format(Browser.FILE_DATE_FORMATTER), browserLabel, context.getClass().getSimpleName(), label);  
	}
	
	public BrowserContext getContext() {
		return context;
	}

	public LocalDateTime getTime() {
		return time;
	}
	
	public Browser getBrowser() {
		return context.getBrowser();
	}
	
	public <T> T getBrowser(Class<T> browser) {
		return context.getBrowser(browser);
	}
	
	public WebDriver getDriver() {
		return context.getDriver();
	}
	
	public <T> T getDriver(Class<T> driver) {
		return context.getDriver(driver);
	}
	
	public void saveFile(String content, String name, String extension) {
		context.saveFile(content, getEventFileName(name), extension);
	}
	
	public void saveFile(byte[] bytes, String name, String extension) {
		context.saveFile(bytes, getEventFileName(name), extension);
	}
	
	public void saveFile(File file, String name, String extension) {
		context.saveFile(file, getEventFileName(name), extension);
	}
	
	protected String getEventFileName(String name) {
		return BrowserUtils.join(Browser.LABEL_DELIMITER, filePrefix, name);
	}
	
	protected String generateLabel() {
		String name = getClass().getSimpleName();
		String endTrim = "Event";
		return name.endsWith(endTrim) ? name.substring(0, name.length() - endTrim.length()) : name;
	}
	
}
