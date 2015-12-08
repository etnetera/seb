package com.etnetera.qa.seleniumbrowser.test.selenium.browser;

import com.etnetera.qa.seleniumbrowser.browser.BrowserConfiguration;

public class BrowserConfig extends BrowserConfiguration {

	@Override
	protected String getBaseUrlDef() {
		return "http://www.etnetera.cz";
	}

}
