package com.etnetera.qa.seleniumbrowser.test.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.etnetera.qa.seleniumbrowser.element.BrowserElement;
import com.etnetera.qa.seleniumbrowser.module.Module;
import com.etnetera.qa.seleniumbrowser.page.Page;
import com.etnetera.qa.seleniumbrowser.page.PageConfig;

@PageConfig(url = "http://www.etnetera.cz/archiv")
public class ArchiveHomePage extends Page {

	@FindBy(id = "vyhledavani")
	protected BrowserElement searchEl;
	
	protected BrowserElement searchInput;

	public BrowserElement getSearchInput() {
		return searchInput;
	}

	@Override
	protected void setup() {
		super.setup();
		searchInput = searchEl.findOne(By.cssSelector("input[name=queryORWords2]"), Module.class);
	}
	
}
