package com.etnetera.qa.seleniumbrowser.page;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.context.VerificationException;

public class PageManager {
	
	/**
	 * Check if browser is at given page.
	 * 
	 * @param page
	 * @return
	 */
	public static boolean verifyAt(Page page) {
		try {
			goTo(page);
			return true;
		} catch (VerificationException e) {
			return false;
		}
	}
	
	/**
	 * Check if browser is at given page class.
	 * 
	 * @param page
	 * @return
	 */
	public static boolean verifyAt(Class<? extends Page> page, Browser browser) {
		try {
			goTo(page, browser);
			return true;
		} catch (VerificationException e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Page> T goTo(T page) {
		return (T) page.goTo();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Page> T goTo(Class<T> page, Browser browser) {
		return (T) construct(page, browser).goTo();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Page> T init(Class<T> page, Browser browser) {
		return (T) construct(page, browser).init();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Page> T init(T page) {
		return (T) page.init();
	}
	
	@SuppressWarnings("unchecked")
	public static Page initOne(Browser browser, Object firstPage, Object ... anotherPages) {
		List<Object> pages = new ArrayList<>(Arrays.asList(anotherPages));
		pages.add(0, firstPage);
		boolean verified = false;
		for (Object page : pages) {
			if (page instanceof Page) {
				verified = verifyAt((Page) page);
			} else {
				verified = verifyAt((Class<? extends Page>) page, browser);
			}
			if (verified) return ((page instanceof Page) ? (Page) page : browser.getPage());
		}
		throw new VerificationException("Unable to init any of given pages " + String.join(", ", pages.stream().map(p -> {
			return (String) ((p instanceof Page) ? ((Page) p).getClass().getName() : ((Class<? extends Page>) p).getName());
		}).collect(Collectors.toList())));
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Page> T construct(Class<T> page, Browser browser) {
		try {
			Constructor<T> ctor = page.getConstructor();
			return (T) ctor.newInstance().with(browser);
		} catch (Exception e) {
			throw new PageConstructException("Unable to construct page " + page.getName(), e);
		}
	}

}
