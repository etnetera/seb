package com.etnetera.qa.seleniumbrowser.listener.event;

import org.openqa.selenium.By;

abstract public class FindByEvent extends WebElementEvent {

	protected By by;

	public By getBy() {
		return by;
	}

	public void setBy(By by) {
		this.by = by;
	}
	
}
