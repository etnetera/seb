package com.etnetera.qa.seleniumbrowser.page;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.context.VerificationException;
import com.etnetera.qa.seleniumbrowser.element.ElementFieldDecorator;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterPageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforePageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnPageInitExceptionEvent;

/**
 * Basic page which supports elements and modules auto loading 
 * with fluent redirection to another pages.
 * 
 * @author zdenek
 */
abstract public class Page implements BrowserContext {

	protected String uri;

	protected String uriRegex;

	protected String baseUrl;

	protected String baseUrlRegex;

	protected Boolean urlVerification;
	
	protected Double waitTimeout;
	
	protected Double waitRetryInterval;

	protected Browser browser;
	
	public Page() {
		PageConfig config = getClass().getDeclaredAnnotation(PageConfig.class);
		if (config != null) {
			if (config.uri().length > 0)
				uri = config.uri()[0];
			if (config.uriRegex().length > 0)
				uriRegex = config.uriRegex()[0];
			if (config.baseUrl().length > 0)
				baseUrl = config.baseUrl()[0];
			if (config.baseUrlRegex().length > 0)
				baseUrlRegex = config.baseUrlRegex()[0];
			if (config.urlVerification().length > 0)
				urlVerification = config.urlVerification()[0];
			if (config.waitTimeout().length > 0)
				waitTimeout = config.waitTimeout()[0];
			if (config.waitRetryInterval().length > 0)
				waitRetryInterval = config.waitRetryInterval()[0];
		}
	}
	
	public Page with(Browser browser) {
		this.browser = browser;
		if (baseUrl == null)
			baseUrl = browser.getBaseUrl();
		if (baseUrlRegex == null)
			baseUrlRegex = browser.getBaseUrlRegex();
		if (urlVerification == null)
			urlVerification = browser.isUrlVerification();
		if (waitTimeout == null)
			waitTimeout = browser.getWaitTimeout();
		if (waitRetryInterval == null)
			waitRetryInterval = browser.getWaitRetryInterval();
		return this;
	}

	public String getUri() {
		return uri;
	}

	public String getUriRegex() {
		return uriRegex;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getBaseUrlRegex() {
		return baseUrlRegex;
	}

	public boolean isUrlVerification() {
		return urlVerification == null ? false : urlVerification;
	}

	public String getUrl() {
		String uri = getUri();
		return uri == null ? null : (getBaseUrl() + uri);
	}

	public String getUrlRegex() {
		String baseUrlRegex = getBaseUrlRegex();
		if (baseUrlRegex == null)
			return null;
		String uriRegex = getUriRegex();
		if (uriRegex == null) {
			String uri = getUri(); 
			if (uri == null)
				return null;
			uriRegex = Pattern.quote(uri) + ".*";
		}
		return baseUrlRegex + uriRegex;
	}

	public Page goTo() {
		String url = getUrl();
		if (url == null)
			throw new PageException("It is not possible to go to page without url " + this.getClass().getName());
		goToUrl(url);
		return init();
	}

	final public Page init() {
		try {
			triggerEvent(constructEvent(BeforePageInitEvent.class).with(this));
			beforeInit();
			beforeInitElements();
			initElements();
			afterInitElements();
			beforeVerify();
			verify();
			afterVerify();
			setPage(this);
			afterInit();
		} catch (Exception e) {
			triggerEvent(constructEvent(OnPageInitExceptionEvent.class).with(this, e));
			throw e;
		}
		triggerEvent(constructEvent(AfterPageInitEvent.class).with(this));
		return this;
	}
	
	protected void initElements() {
		PageFactory.initElements(
				new ElementFieldDecorator(new DefaultElementLocatorFactory(this), this),
				this);
	}

	@Override
	public BrowserContext getContext() {
		return browser;
	}
	
	@Override
	public Browser getBrowser() {
		return browser;
	}

	@Override
	public double getWaitTimeout() {
		return waitTimeout == null ? 0 : waitTimeout;
	}
	
	public void setWaitTimeout(double waitTimeout) {
		this.waitTimeout = waitTimeout;
	}

	@Override
	public double getWaitRetryInterval() {
		return waitRetryInterval == null ? 0 : waitRetryInterval;
	}
	
	public void setWaitRetryInterval(double waitRetryInterval) {
		this.waitRetryInterval = waitRetryInterval;
	}

	@Override
	public List<WebElement> findElements(By by) {
		return browser.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return browser.findElement(by);
	}

	public void verify() throws VerificationException {
		try {
			verifyUrl();
			verifyThis();
		} catch (Exception e) {
			throw new VerificationException("Page is wrong " + this.getClass().getName(), e);
		}
	}

	protected void verifyUrl() {
		if (!isUrlVerification())
			return;
		String urlRegex = getUrlRegex();
		if (urlRegex == null)
			return;
		String currentUrl = getDriver().getCurrentUrl(); 
		if (currentUrl != null && !currentUrl.matches(urlRegex)) {
			throw new VerificationException("Unable to verify page url for " + getClass().getName()
					+ " using url regex " + urlRegex + " against current url " + currentUrl);
		}
	}

	protected void verifyThis() {
		// check if we are on right page
	}

	protected void beforeInit() {
		// do whatever you want
	}
	
	protected void beforeInitElements() {
		// do whatever you want
	}
	
	protected void afterInitElements() {
		// do whatever you want
	}
	
	protected void beforeVerify() {
		// do whatever you want
	}
	
	protected void afterVerify() {
		// do whatever you want
	}
	
	protected void afterInit() {
		// do whatever you want
	}

}
