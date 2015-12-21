package com.etnetera.qa.seleniumbrowser.element;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.module.Module;

public class ElementLoader {
	
	protected BrowserContext context;
	
	public ElementLoader(BrowserContext context) {
		this.context = context;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends WebElement> T findElement(SearchContext searchContext, By by, Class<T> elementCls, boolean optional) {
		if (searchContext instanceof WebElement && context.isNotPresent((WebElement) searchContext)) {
			return (T) convertElementToModule((Class<? extends Module>) elementCls, (WebElement) searchContext);
		}
		return findElement(() -> searchContext.findElement(by), elementCls, optional);
	}
	
	public <T extends WebElement> List<T> findElements(SearchContext searchContext, By by, Class<T> elementCls) {
		if (searchContext instanceof WebElement && context.isNotPresent((WebElement) searchContext)) {
			return new ArrayList<>();
		}
		return findElements(() -> searchContext.findElements(by), elementCls);
	}

	@SuppressWarnings("unchecked")
	public <T extends WebElement> T findElement(Supplier<WebElement> finder, Class<T> elementCls, boolean optional) {
		WebElement element = findWebElement(finder, optional);
		if (element != null && Module.class.isAssignableFrom(elementCls)) {
			return (T) convertElementToModule((Class<? extends Module>) elementCls, element);
		} else {
			return (T) element;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends WebElement> List<T> findElements(Supplier<List<WebElement>> finder, Class<T> elementCls) {
		List<WebElement> elements = findWebElements(finder);
		if (elements != null && Module.class.isAssignableFrom(elementCls)) {
			List<T> modules = new ArrayList<>();
			for (WebElement element : elements) {
				modules.add((T) convertElementToModule((Class<? extends Module>) elementCls, element));
			}
			return modules;
		} else {
			return (List<T>) elements;
		}
	}
	
	protected WebElement findWebElement(Supplier<WebElement> finder, boolean optional) {
		WebElement element;
		try {
			element = finder.get();
			element.isDisplayed();
		} catch (final NoSuchElementException nsee) {
			if (optional) {
				element = new MissingElement(nsee);
			} else {
				throw nsee;
			}
		}
		return element;
	}
	
	protected List<WebElement> findWebElements(Supplier<List<WebElement>> finder) {
		return finder.get();
	}
	
	protected Module convertElementToModule(Class<? extends Module> moduleCls, WebElement element) {
		return context.initModule(moduleCls, element);
	}
	
}
