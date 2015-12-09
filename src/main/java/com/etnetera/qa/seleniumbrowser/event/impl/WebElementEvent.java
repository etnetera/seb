package com.etnetera.qa.seleniumbrowser.event.impl;

import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;

abstract public class WebElementEvent extends BrowserEvent {

	protected WebElement element;

	public WebElementEvent with(WebElement element) {
		this.element = element;
		return this;
	}
	
	public WebElement getElement() {
		return element;
	}
	
}
