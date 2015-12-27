package com.etnetera.qa.seleniumbrowser.element;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;

/**
 * Decorator used with PageFactory which allows to inject MissingWebElement when
 * OptionalWebElement is not found.
 */
public class BrowserFieldDecorator implements FieldDecorator {

	protected BrowserContext context;

	public BrowserFieldDecorator(BrowserContext context) {
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
		
		if (!BrowserElement.class.isAssignableFrom(elementCls))
			elementCls = BrowserElement.class;

		if (!isList) {
			return context.getBrowser().getElementLoader().findOne(context, loader, field, (Class<? extends BrowserElement>) elementCls, field.isAnnotationPresent(OptionalElement.class));
		} else {
			return context.getBrowser().getElementLoader().find(context, loader, field, (Class<? extends BrowserElement>) elementCls);
		}
	}

	protected boolean isSupported(Field field) {
		return field.isAnnotationPresent(FindByDefault.class) || field.isAnnotationPresent(FindBy.class)
				|| field.isAnnotationPresent(FindBys.class) || field.isAnnotationPresent(FindAll.class);
	}

}
