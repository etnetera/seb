package com.etnetera.qa.seleniumbrowser.test.selenium.test;

import org.junit.After;
import org.junit.Before;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.test.selenium.browser.BrowserConfig;

abstract public class BrowserTest {

	protected Browser browser;
	
	@Before
	public void before() {
		browser = new Browser(new BrowserConfig());
	}
	
	@After
	public void after() {
		if (browser != null) browser.quit();
	}

}
