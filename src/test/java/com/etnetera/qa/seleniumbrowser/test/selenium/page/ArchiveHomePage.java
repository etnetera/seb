package com.etnetera.qa.seleniumbrowser.test.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.etnetera.qa.seleniumbrowser.module.Module;
import com.etnetera.qa.seleniumbrowser.page.Page;
import com.etnetera.qa.seleniumbrowser.page.PageConfig;

@PageConfig(url = "http://www.etnetera.cz/archiv")
public class ArchiveHomePage extends Page {

	@FindBy(id = "vyhledavani")
	protected Module searchEl;
	
	protected Module searchInput;

	public Module getSearchInput() {
		return searchInput;
	}

	@Override
	protected void setup() {
		super.setup();
		searchInput = searchEl.findElement(By.cssSelector("input[name=queryORWords]"), Module.class);
	}
	
}
