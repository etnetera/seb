package com.etnetera.qa.seleniumbrowser.test.selenium.test;

import org.junit.Test;

import com.etnetera.qa.seleniumbrowser.test.selenium.page.HomePage;

public class HomePageTest extends BrowserTest {
	
	@Test
	public void valid() {
		browser.goTo(HomePage.class);
	}
	
}
