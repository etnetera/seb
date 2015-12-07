package com.etnetera.qa.seleniumbrowser.logic;

import java.lang.reflect.Constructor;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;

public class LogicManager {
	
	@SuppressWarnings("unchecked")
	public static <T extends Logic> T init(T logic) {
		return (T) logic.init();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Logic> T init(Class<T> logic, BrowserContext context) {
		return (T) create(logic, context).init();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Logic> T create(Class<T> logic, BrowserContext context) {
		try {
			Constructor<T> ctor = logic.getConstructor();
			return (T) ctor.newInstance().with(context);
		} catch (Exception e) {
			throw new LogicConstructException("Unable to construct logic " + logic.getName(), e);
		}
	}

}
