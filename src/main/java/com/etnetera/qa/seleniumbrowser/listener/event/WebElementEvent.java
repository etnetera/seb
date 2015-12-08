package com.etnetera.qa.seleniumbrowser.listener.event;

import org.openqa.selenium.WebElement;

abstract public class WebElementEvent extends BrowserEvent {

	protected WebElement element;

	public WebElement getElement() {
		return element;
	}

	public void setElement(WebElement element) {
		this.element = element;
	}
	
}
