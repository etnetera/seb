package com.etnetera.qa.seleniumbrowser.test.selenium.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.test.selenium.configuration.ChainedBrowserConfig;
import com.etnetera.qa.seleniumbrowser.test.selenium.configuration.DefaultBrowserConfig;
import com.etnetera.qa.seleniumbrowser.test.selenium.configuration.SystemPropertyBrowserConfig;
import com.etnetera.qa.seleniumbrowser.test.selenium.page.HomePage;

public class HomePageTest2 {
	
	protected Browser browser;
	
	@Before
	public void before() {
		browser = new Browser(new ChainedBrowserConfig(new SystemPropertyBrowserConfig(), new DefaultBrowserConfig()));
	}
	
	@Test
	public void valid() {
		browser.useEnclosingMethodLabel();
		browser.goTo(HomePage.class);
		browser.report("On homepage");
	}
	
	@After
	public void after() {
		if (browser != null) browser.quit();
	}
	
}
