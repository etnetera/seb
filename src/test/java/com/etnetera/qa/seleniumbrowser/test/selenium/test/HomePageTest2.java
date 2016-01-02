/* Copyright 2016 Etnetera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
