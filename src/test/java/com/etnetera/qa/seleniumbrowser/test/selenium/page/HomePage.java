package com.etnetera.qa.seleniumbrowser.test.selenium.page;

import org.junit.Assert;

import com.etnetera.qa.seleniumbrowser.page.Page;
import com.etnetera.qa.seleniumbrowser.page.PageConfig;

@PageConfig(uri = "")
public class HomePage extends Page {

	@Override
	protected void verifyThis() {
		super.verifyThis();
		Assert.assertTrue("Title contains Etnetera", getDriver().getTitle().contains("Etnetera"));
	}
	
}
