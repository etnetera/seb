package com.etnetera.qa.seleniumbrowser.module;

import org.openqa.selenium.WebDriverException;

public class ModuleConstructException extends WebDriverException {

	private static final long serialVersionUID = -456856708024115000L;

	public ModuleConstructException(String message, Throwable cause) {
		super(message, cause);
	}

}
