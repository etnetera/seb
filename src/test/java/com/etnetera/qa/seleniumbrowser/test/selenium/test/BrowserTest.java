package com.etnetera.qa.seleniumbrowser.test.selenium.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import com.etnetera.qa.seleniumbrowser.browser.Browser;

abstract public class BrowserTest {

	@Rule public TestName name = new TestName();
	
	protected Browser browser;
	
	@Before
	public void before() {
		browser = new Browser();
		browser.setLabel(getClass().getSimpleName(), name.getMethodName());
	}
	
	@After
	public void after() {
		if (browser != null) browser.quit();
	}

}
