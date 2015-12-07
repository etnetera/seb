package com.etnetera.qa.seleniumbrowser.page;

import org.openqa.selenium.WebDriverException;

public class PageException extends WebDriverException {

	private static final long serialVersionUID = 7194523048346402725L;

	public PageException() {
		super();
	}

	public PageException(String message, Throwable cause) {
		super(message, cause);
	}

	public PageException(String message) {
		super(message);
	}

	public PageException(Throwable cause) {
		super(cause);
	}

}
