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
package cz.etnetera.seb.element;

import java.util.AbstractList;
import java.util.List;

import org.openqa.selenium.WebElement;

import cz.etnetera.seb.SebContext;

/**
 * List which holds elements loaded using {@link SebElementLoader}.
 */
public class SebElementList extends AbstractList<SebElement> {

	protected SebContext context;
	
	protected List<WebElement> webElements;
	
	protected Class<? extends SebElement> elementCls;
	
	public SebElementList(SebContext context, List<WebElement> webElements,
			Class<? extends SebElement> elementCls) {
		this.context = context;
		this.webElements = webElements;
		this.elementCls = elementCls;
	}

	@Override
	public SebElement get(int index) {
		return context.initSebElement(elementCls, webElements.get(index), false);
	}

	@Override
	public int size() {
		return webElements.size();
	}

}
