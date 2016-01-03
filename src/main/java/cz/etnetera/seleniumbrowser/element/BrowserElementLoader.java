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
package cz.etnetera.seleniumbrowser.element;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

import cz.etnetera.seleniumbrowser.browser.BrowserContext;

/**
 * {@link BrowserContext} element loader. It wraps {@link WebElement}
 * into {@link BrowserElement} and allows set optional elements, which
 * are not needed to be present immediately.
 */
public class BrowserElementLoader {

	public <T extends BrowserElement> T findOne(BrowserContext context, By by, Class<T> elementCls, boolean optional) {
		return initBrowserElement(context,
				proxyForLocator(context.getClass().getClassLoader(), context.createElementLocator(by)),
				elementCls, optional);
	}

	public <T extends BrowserElement> T findOne(BrowserContext context, ClassLoader loader, Field field, Class<T> elementCls,
			boolean optional) {
		return initBrowserElement(context,
				proxyForLocator(loader, context.createElementLocator(field)),
				elementCls, optional);
	}

	public <T extends BrowserElement> List<T> find(BrowserContext context, By by, Class<T> elementCls) {
		return initBrowserElements(context,
				proxyForListLocator(context.getClass().getClassLoader(), context.createElementLocator(by)),
				elementCls);
	}

	public <T extends BrowserElement> List<T> find(BrowserContext context, ClassLoader loader, Field field, Class<T> elementCls) {
		return initBrowserElements(context,
				proxyForListLocator(loader, context.createElementLocator(field)),
				elementCls);
	}

	public <T extends BrowserElement> T initBrowserElement(BrowserContext context, WebElement webElement,
			Class<T> elementCls, boolean optional) {
		return (T) context.initBrowserElement(elementCls, webElement, optional);
	}

	@SuppressWarnings("unchecked")
	public <T extends BrowserElement> List<T> initBrowserElements(BrowserContext context, List<WebElement> webElements,
			Class<T> elementCls) {
		return (List<T>) new BrowserElementList(context, webElements, elementCls);
	}

	protected WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
		InvocationHandler handler = new LocatingElementHandler(locator);
		return (WebElement) Proxy.newProxyInstance(loader,
				new Class[] { WebElement.class, WrapsElement.class, Locatable.class }, handler);
	}

	@SuppressWarnings("unchecked")
	protected List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
		InvocationHandler handler = new LocatingElementListHandler(locator);
		return (List<WebElement>) Proxy.newProxyInstance(loader, new Class[] { List.class }, handler);
	}

}
