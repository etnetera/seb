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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import cz.etnetera.seb.SebContext;

/**
 * {@link PageFactory} decorator which uses {@link SebElementLoader}
 * for list loading. 
 */
public class SebFieldDecorator implements FieldDecorator {

	protected static final String TYPE_NAME_PREFIX = "class ";
	
	protected SebContext context;

	public SebFieldDecorator(SebContext context) {
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
			elementCls = getClassFromType(listType);
			if (!WebElement.class.isAssignableFrom(elementCls)) {
				return null;
			}
			isList = true;
		} else {
			return null;
		}
		
		if (!SebElement.class.isAssignableFrom(elementCls))
			elementCls = SebElement.class;

		if (!isList) {
			return context.getSeb().getElementLoader().findOne(context, loader, field, (Class<? extends SebElement>) elementCls, !field.isAnnotationPresent(RequiredElement.class));
		} else {
			return context.getSeb().getElementLoader().find(context, loader, field, (Class<? extends SebElement>) elementCls);
		}
	}

	protected boolean isSupported(Field field) {
		return field.isAnnotationPresent(FindByDefault.class) || field.isAnnotationPresent(FindBy.class)
				|| field.isAnnotationPresent(FindBys.class) || field.isAnnotationPresent(FindAll.class);
	}
	
	protected Class<?> getClassFromType(Type type) {
		if (type == null)
			return null;
		String className = type.getTypeName();
		if (className.startsWith(TYPE_NAME_PREFIX))
			className = className.substring(TYPE_NAME_PREFIX.length());
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

}
