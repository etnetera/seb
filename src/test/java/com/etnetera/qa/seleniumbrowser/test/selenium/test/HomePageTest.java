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

import org.junit.Test;

import com.etnetera.qa.seleniumbrowser.test.selenium.page.HomePage;

public class HomePageTest extends BrowserTest {
	
	@Test
	public void valid() {
		browser.goTo(HomePage.class);
	}
	
}
