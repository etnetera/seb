package com.etnetera.qa.seleniumbrowser.module;

import java.lang.reflect.Constructor;

import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;

public class ModuleManager {
	
	@SuppressWarnings("unchecked")
	public static <T extends Module> T init(T module) {
		return (T) module.init();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Module> T init(Class<T> module, BrowserContext context, WebElement element) {
		return (T) create(module, context, element).init();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Module> T create(Class<T> module, BrowserContext context, WebElement element) {
		try {
			Constructor<T> ctor = module.getConstructor();
			return (T) ctor.newInstance().with(context, element);
		} catch (Exception e) {
			throw new ModuleConstructException("Unable to construct module " + module.getName(), e);
		}
	}

}
