package com.etnetera.qa.seleniumbrowser.test.selenium.test;

import org.junit.Test;

import com.etnetera.qa.seleniumbrowser.test.selenium.page.ArchiveHomePage;

public class ArchiveHomePageTest extends BrowserTest {
	
	@Test
	public void valid() {
		ArchiveHomePage page = browser.goTo(ArchiveHomePage.class);
		browser.waiting(2).sleep();
		page.getSearchInput().click();
		browser.waiting(2).sleep();
		page.getSearchInput().blur();
		browser.waiting(10).sleep();
	}
	
}
