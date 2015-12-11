package com.etnetera.qa.seleniumbrowser.page;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.context.VerificationException;

public class PageManager {

	public static <T extends Page> T goToSafely(T page) {
		try {
			return goTo(page);
		} catch (VerificationException e) {
			return null;
		}
	}

	public static <T extends Page> T goToSafely(Class<T> page, Browser browser) {
		try {
			return goTo(page, browser);
		} catch (VerificationException e) {
			return null;
		}
	}

	public static <T extends Page> T initSafely(Class<T> page, Browser browser) {
		try {
			return init(page, browser);
		} catch (VerificationException e) {
			return null;
		}
	}

	public static <T extends Page> T initSafely(T page) {
		try {
			return init(page);
		} catch (VerificationException e) {
			return null;
		}
	}

	public static Page initOneSafely(Browser browser, Object firstPage, Object... anotherPages) {
		try {
			return initOne(browser, firstPage, anotherPages);
		} catch (VerificationException e) {
			return null;
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
	public static Page initOne(Browser browser, Object... pages) {
		Page verifiedPage = null;
		for (Object page : pages) {
			if (page instanceof Page) {
				verifiedPage = initSafely((Page) page);
			} else {
				verifiedPage = initSafely((Class<? extends Page>) page, browser);
			}
			if (verifiedPage != null)
				return verifiedPage;
		}
		throw new VerificationException(
				"Unable to init any of given pages " + String.join(", ", Arrays.asList(pages).stream().map(p -> {
					return (String) ((p instanceof Page) ? ((Page) p).getClass().getName()
							: ((Class<? extends Page>) p).getName());
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
