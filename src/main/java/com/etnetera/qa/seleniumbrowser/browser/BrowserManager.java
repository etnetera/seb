package com.etnetera.qa.seleniumbrowser.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserManager {

	public static final Logger logger = LoggerFactory.getLogger(BrowserManager.class);
	
	public static void getUrl(Browser browser, String url) {
		logger.debug("Redirect: " + url);
		browser.getDriver().get(url);
	}

}
