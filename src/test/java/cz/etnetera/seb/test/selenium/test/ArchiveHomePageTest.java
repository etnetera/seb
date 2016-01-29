/* Copyright 2016 Etnetera a.s.
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
package cz.etnetera.seb.test.selenium.test;

import org.junit.Assert;
import org.junit.Test;

import cz.etnetera.seb.test.selenium.page.ArchiveHomePage;
import cz.etnetera.seb.test.selenium.page.HomePage;

public class ArchiveHomePageTest extends SebTest {
	
	@Test
	public void valid() {
		ArchiveHomePage page = seb.goTo(ArchiveHomePage.class);
		
		Assert.assertTrue("Search input has class search_input", page.getSearchInput().hasClass("search_input"));
		Assert.assertFalse("Search input has no class search_input2", page.getSearchInput().hasClass("search_input2"));
		
		seb.waiting(2).sleep();
		page.getSearchInput().click();
		seb.waiting(2).sleep();
		page.getSearchInput().blur();
		seb.waiting(2).sleep();
	}
	
	@Test
	public void onePageCheck() {
		seb.goTo(HomePage.class);
		seb.initOnePage(ArchiveHomePage.class, HomePage.class);
		Assert.assertEquals("Page is HomePage", HomePage.class, seb.getPage().getClass());
	}
	
}
