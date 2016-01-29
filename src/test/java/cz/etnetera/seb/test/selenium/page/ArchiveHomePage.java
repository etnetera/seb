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
package cz.etnetera.seb.test.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import cz.etnetera.seb.element.SebElement;
import cz.etnetera.seb.module.Module;
import cz.etnetera.seb.page.Page;
import cz.etnetera.seb.page.PageConfig;

@PageConfig(url = "http://www.etnetera.cz/archiv")
public class ArchiveHomePage extends Page {

	@FindBy(id = "vyhledavani")
	protected SebElement searchEl;
	
	protected SebElement searchInput;

	public SebElement getSearchInput() {
		return searchInput;
	}

	@Override
	protected void setup() {
		super.setup();
		searchInput = searchEl.findOne(By.cssSelector("input[name=queryORWords]"), Module.class);
	}
	
}
