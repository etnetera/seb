package com.etnetera.qa.seleniumbrowser.listener;

import org.openqa.selenium.WebDriverException;

public class EventConstructException extends WebDriverException {

	private static final long serialVersionUID = 8482963353364145739L;

	public EventConstructException(String message, Throwable cause) {
		super(message, cause);
	}

}
