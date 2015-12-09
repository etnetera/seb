package com.etnetera.qa.seleniumbrowser.context;

import com.etnetera.qa.seleniumbrowser.browser.BrowserException;

public class VerificationException extends BrowserException {

	private static final long serialVersionUID = 911749792790433730L;

	public VerificationException() {
		super();
	}

	public VerificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public VerificationException(String message) {
		super(message);
	}

	public VerificationException(Throwable cause) {
		super(cause);
	}

}
