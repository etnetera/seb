package com.etnetera.qa.seleniumbrowser.test.selenium.test;

import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.test.selenium.configuration.BrowserConfig;
import com.etnetera.qa.seleniumbrowser.test.selenium.page.HomePage;

public class HomePageTest2 {

	protected Browser browser;

	@Before
	public void before() {
		browser = new Browser(BrowserConfig.class);
	}

	@Test
	public void valid() {
		browser.useEnclosingMethodLabel();
		browser.goTo(HomePage.class);
		Assert.assertTrue("Title is valid", browser.getDriver().getTitle().contains(browser.getData("custom", Properties.class).getProperty("title")));
		browser.report(browser.getProperty("report.onHomepage", "On homepage 2"));
		Assert.assertEquals("UTF-8 string is loaded fine", "Žluťoučký kůň", browser.getProperty("unicode.test"));
	}

	@After
	public void after() {
		if (browser != null)
			browser.quit();
	}

}
