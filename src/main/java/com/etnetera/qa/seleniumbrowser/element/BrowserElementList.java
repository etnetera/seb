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
package com.etnetera.qa.seleniumbrowser.element;

import java.util.AbstractList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;

/**
 * List which holds elements loaded using {@link BrowserElementLoader}.
 */
public class BrowserElementList extends AbstractList<BrowserElement> {

	protected BrowserContext context;
	
	protected List<WebElement> webElements;
	
	protected Class<? extends BrowserElement> elementCls;
	
	public BrowserElementList(BrowserContext context, List<WebElement> webElements,
			Class<? extends BrowserElement> elementCls) {
		this.context = context;
		this.webElements = webElements;
		this.elementCls = elementCls;
	}

	@Override
	public BrowserElement get(int index) {
		return context.initBrowserElement(elementCls, webElements.get(index), false);
	}

	@Override
	public int size() {
		return webElements.size();
	}

}
