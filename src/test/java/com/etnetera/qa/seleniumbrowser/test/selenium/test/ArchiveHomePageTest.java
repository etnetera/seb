package com.etnetera.qa.seleniumbrowser.test.selenium.test;

import org.junit.Assert;
import org.junit.Test;

import com.etnetera.qa.seleniumbrowser.test.selenium.page.ArchiveHomePage;

public class ArchiveHomePageTest extends BrowserTest {
	
	@Test
	public void valid() {
		ArchiveHomePage page = browser.goTo(ArchiveHomePage.class);
		
		Assert.assertTrue("Search input has class search_input", page.getSearchInput().hasClass("search_input"));
		Assert.assertFalse("Search input has no class search_input2", page.getSearchInput().hasClass("search_input2"));
		
		browser.waiting(2).sleep();
		page.getSearchInput().click();
		browser.waiting(2).sleep();
		page.getSearchInput().blur();
		browser.waiting(2).sleep();
	}
	
}
