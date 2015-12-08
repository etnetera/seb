package com.etnetera.qa.seleniumbrowser.listener.event;

import java.util.Date;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;

abstract public class BrowserEvent {

	protected BrowserContext context;
	
	protected Date time;
	
	protected long number;
	
	protected String browserLabel;
	
	protected String label;
	
	protected String filePrefix;

	/**
	 * Is called before event is triggered.
	 */
	public void init() {
		browserLabel = context.getBrowser().getLabel();
		label = generateLabel();
		filePrefix = String.join("-", String.valueOf(number), browserLabel, label);  
	}
	
	public BrowserContext getContext() {
		return context;
	}

	public void setContext(BrowserContext context) {
		this.context = context;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}
	
	protected String getFilename(String label, String extension) {
		return filePrefix + "-";
	}
	
	protected String generateLabel() {
		return getClass().getSimpleName();
	}
	
}
