package com.etnetera.qa.seleniumbrowser.element;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

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

/**
 * Decorator used with PageFactory which allows to inject MissingWebElement when
 * OptionalWebElement is not found.
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
			return context.getBrowser().getElementLoader().findElement(() -> proxyForLocator(field, loader, locator), (Class<? extends WebElement>) elementCls, field.isAnnotationPresent(OptionalElement.class));
		} else {
			return context.getBrowser().getElementLoader().findElements(() -> proxyForListLocator(loader, locator), (Class<? extends WebElement>) elementCls);
		}
	}

	protected boolean isSupported(Field field) {
		return field.isAnnotationPresent(FindByDefault.class) || field.isAnnotationPresent(FindBy.class)
				|| field.isAnnotationPresent(FindBys.class) || field.isAnnotationPresent(FindAll.class);
	}

	protected WebElement proxyForLocator(Field field, ClassLoader loader, ElementLocator locator) {
		InvocationHandler handler = new LocatingElementHandler(locator);
		return (WebElement) Proxy.newProxyInstance(loader,
				new Class[] { WebElement.class, WrapsElement.class, Locatable.class }, handler);
	}

	@SuppressWarnings("unchecked")
	protected List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
		InvocationHandler handler = new LocatingElementListHandler(locator);
		return (List<WebElement>) Proxy.newProxyInstance(loader, new Class[] { List.class }, handler);
	}

	protected Module convertElementToModule(Class<? extends Module> moduleCls, WebElement element) {
		return context.initModule(moduleCls, element);
	}

}
