package com.etnetera.qa.seleniumbrowser.event.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

abstract public class FindByEvent extends WebElementEvent {

	protected By by;
	
	public FindByEvent with(By by, WebElement element) {
		this.by = by;
		super.with(element);
		return this;
	}

	public By getBy() {
		return by;
	}
	
}
