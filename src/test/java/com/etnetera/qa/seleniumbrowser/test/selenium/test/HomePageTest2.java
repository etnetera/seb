package com.etnetera.qa.seleniumbrowser.test.selenium.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.test.selenium.configuration.ChainedBrowserConfig;
import com.etnetera.qa.seleniumbrowser.test.selenium.configuration.DefaultBrowserConfig;
import com.etnetera.qa.seleniumbrowser.test.selenium.configuration.PropertiesBrowserConfig;
import com.etnetera.qa.seleniumbrowser.test.selenium.page.HomePage;

public class HomePageTest2 {

	protected Browser browser;

	@Before
	public void before() {
		browser = new Browser(
				new ChainedBrowserConfig()
						.addConfiguration("props", new PropertiesBrowserConfig().addSystemProperties()
								.addResourceProperties("custom",
										"com.etnetera.qa.seleniumbrowser.test.selenium/customProperties.properties")
						.addDefaultProperties()).addConfiguration("def", new DefaultBrowserConfig()));
	}

	@Test
	public void valid() {
		browser.useEnclosingMethodLabel();
		browser.goTo(HomePage.class);
		browser.report(browser.getProperty("report.onHomepage", "On homepage 2"));
	}

	@After
	public void after() {
		if (browser != null)
			browser.quit();
	}

}
