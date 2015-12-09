package com.etnetera.qa.seleniumbrowser.page;

import com.etnetera.qa.seleniumbrowser.browser.BrowserException;

public class PageException extends BrowserException {

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
