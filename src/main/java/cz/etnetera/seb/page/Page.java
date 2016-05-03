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
package cz.etnetera.seb.page;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.SebContext;
import cz.etnetera.seb.VerificationException;
import cz.etnetera.seb.event.impl.AfterPageInitEvent;
import cz.etnetera.seb.event.impl.BeforePageInitEvent;

/**
 * Basic page which supports elements and modules auto loading 
 * with fluent redirection to another pages.
 */
abstract public class Page implements SebContext {

	protected String uri;

	protected String uriRegex;

	protected String baseUrl;

	protected String baseUrlRegex;
	
	protected String url;

	protected String urlRegex;

	protected Boolean urlVerification;
	
	protected Double waitTimeout;
	
	protected Double waitRetryInterval;
	
	protected Double waitBeforePageInitTimeout;

	protected Seb seb;
	
	public Page with(Seb seb) {
		this.seb = seb;
		configureFromAnnotation();
		configureFromSeb();
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
		if (url != null)
			return url;
		String uri = getUri();
		return uri == null ? null : (getBaseUrl() + uri);
	}

	public String getUrlRegex() {
		if (urlRegex != null)
			return urlRegex;
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
			throw new PageException("It is not possible to go to page without url " + this);
		goToUrl(url);
		return init();
	}

	public Page init() {
		if (waitBeforePageInitTimeout != null)
			waiting(waitBeforePageInitTimeout).sleep();
		try {
			triggerEvent(constructEvent(BeforePageInitEvent.class).with(this));
			beforeInit();
			beforeInitElements();
			initElements();
			afterInitElements();
			beforeSetup();
			setup();
			afterSetup();
			beforeVerify();
			verify();
			afterVerify();
			setPage(this);
			afterInit();
		} catch (Exception e) {
			throw e;
		}
		triggerEvent(constructEvent(AfterPageInitEvent.class).with(this));
		return this;
	}

	@Override
	public SebContext getContext() {
		return seb;
	}
	
	@Override
	public Seb getSeb() {
		return seb;
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
	public double getWaitBeforePageInitTimeout() {
		return waitBeforePageInitTimeout == null ? 0 : waitBeforePageInitTimeout;
	}

	public void setWaitBeforePageInitTimeout(double waitBeforePageInitTimeout) {
		this.waitBeforePageInitTimeout = waitBeforePageInitTimeout;
	}

	@Override
	public List<WebElement> findElements(By by) {
		return seb.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return seb.findElement(by);
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
	
	protected void configureFromAnnotation() {
		PageConfig config = getClass().getDeclaredAnnotation(PageConfig.class);
		if (config != null)
			applyAnnotationConfiguration(config);
	}
	
	protected void applyAnnotationConfiguration(PageConfig config) {
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
		if (config.url().length > 0)
			url = config.url()[0];
		if (config.urlRegex().length > 0)
			urlRegex = config.urlRegex()[0];
		if (config.waitTimeout().length > 0)
			waitTimeout = config.waitTimeout()[0];
		if (config.waitRetryInterval().length > 0)
			waitRetryInterval = config.waitRetryInterval()[0];
		if (config.waitBeforePageInitTimeout().length > 0)
			waitBeforePageInitTimeout = config.waitBeforePageInitTimeout()[0];
	}
	
	protected void configureFromSeb() {
		if (seb != null)
			applySebConfiguration(seb);
	}
	
	protected void applySebConfiguration(Seb seb) {
		if (baseUrl == null)
			baseUrl = seb.getBaseUrl();
		if (baseUrlRegex == null)
			baseUrlRegex = seb.getBaseUrlRegex();
		if (urlVerification == null)
			urlVerification = seb.isUrlVerification();
		if (waitTimeout == null)
			waitTimeout = seb.getWaitTimeout();
		if (waitRetryInterval == null)
			waitRetryInterval = seb.getWaitRetryInterval();
		if (waitBeforePageInitTimeout == null)
			waitBeforePageInitTimeout = seb.getWaitBeforePageInitTimeout();
	}
	
	/**
	 * Override this method to initialize
	 * custom elements or do some other things
	 * before verification.
	 */
	protected void setup() {
		// initialize custom elements etc.
	}

	/**
	 * Override this method to perform custom check
	 * after all fields are initiated and setup is done.
	 */
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
	
	protected void beforeSetup() {
		// do whatever you want
	}
	
	protected void afterSetup() {
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
