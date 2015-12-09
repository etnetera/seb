package com.etnetera.qa.seleniumbrowser.browser;

import org.openqa.selenium.WebDriverException;

public class BrowserException extends WebDriverException {

	private static final long serialVersionUID = -6448878303753153195L;

	public BrowserException() {
		super();
	}

	public BrowserException(String message, Throwable cause) {
		super(message, cause);
	}

	public BrowserException(String message) {
		super(message);
	}

	public BrowserException(Throwable cause) {
		super(cause);
	}

}
