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

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.test.selenium.page.HomePage;

public class HomePageTest2 {

	@Test
	public void valid() {
		Seb seb = new Seb().useEnclosingMethodLabel().start();
		seb.goTo(HomePage.class);
		Assert.assertTrue("Title is valid", seb.getDriver().getTitle().contains(seb.getData("custom", Properties.class).getProperty("title")));
		seb.report(seb.getProperty("report.onHomepage", "On homepage 2"));
		Assert.assertEquals("UTF-8 string is loaded fine", "Žluťoučký kůň", seb.getProperty("unicode.test"));
	}

}
