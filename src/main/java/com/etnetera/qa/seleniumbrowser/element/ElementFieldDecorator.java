package com.etnetera.qa.seleniumbrowser.element;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.module.Module;
import com.etnetera.qa.seleniumbrowser.module.ModuleManager;

/**
 * Decorator used with PageFactory which allows to inject MissingWebElement when
 * OptionalWebElement is not found.
 * 
 * @author zdenek
 *
 */
public class ElementFieldDecorator implements FieldDecorator {

	protected ElementLocatorFactory factory;
	
	protected BrowserContext context;

	public ElementFieldDecorator(ElementLocatorFactory factory, BrowserContext context) {
		this.factory = factory;
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	public Object decorate(ClassLoader loader, Field field) {
		if (!isSupported(field))
			return null;
		Class<?> elementCls = null;
		boolean isList = false;
		if (WebElement.class.isAssignableFrom(field.getType())) {
			elementCls = field.getType();
		} else if (List.class.isAssignableFrom(field.getType())) {
			Type genericType = field.getGenericType();
			if (!(genericType instanceof ParameterizedType)) {
				return null;
			}
			Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
			if (!WebElement.class.isAssignableFrom(listType.getClass())) {
				return null;
			}
			elementCls = listType.getClass();
			isList = true;
		} else {
			return null;
		}

		ElementLocator locator = factory.createLocator(field);
		if (locator == null) {
			return null;
		}

		if (!isList) {
			WebElement proxy = proxyForLocator(field, loader, locator);
			if (proxy != null && Module.class.isAssignableFrom(elementCls)) {
				return convertElementToModule((Class<? extends Module>) elementCls, proxy);
			} else {
				return proxy;
			}
		} else {
			List<WebElement> proxy = proxyForListLocator(loader, locator);
			if (proxy != null && Module.class.isAssignableFrom(elementCls)) {
				List<Module> modules = new ArrayList<>();
				for (WebElement element : proxy) {
					modules.add(convertElementToModule((Class<? extends Module>) elementCls, element));
				}
				return modules;
			} else {
				return proxy;
			}
		}
	}
	
	protected boolean isSupported(Field field) {
		return field.isAnnotationPresent(FindBy.class) || field.isAnnotationPresent(FindBys.class) || field.isAnnotationPresent(FindAll.class);
	}

	protected WebElement proxyForLocator(Field field, ClassLoader loader, ElementLocator locator) {
		InvocationHandler handler = new LocatingElementHandler(locator);

		WebElement proxy;
		try {
			proxy = (WebElement) Proxy.newProxyInstance(loader,
					new Class[] { WebElement.class, WrapsElement.class, Locatable.class }, handler);
			proxy.isDisplayed();
		} catch (final NoSuchElementException nsee) {
			if (field.isAnnotationPresent(OptionalElement.class)) {
				proxy = new MissingElement(nsee);
			} else {
				throw nsee;
			}
		}
		return proxy;
	}

	@SuppressWarnings("unchecked")
	protected List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
		InvocationHandler handler = new LocatingElementListHandler(locator);

		List<WebElement> proxy;
		proxy = (List<WebElement>) Proxy.newProxyInstance(loader, new Class[] { List.class }, handler);
		return proxy;
	}

	protected Module convertElementToModule(Class<? extends Module> moduleCls, WebElement element) {
		return ModuleManager.init(moduleCls, context, element);
	}

}
