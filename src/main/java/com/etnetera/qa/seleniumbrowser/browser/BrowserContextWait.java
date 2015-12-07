package com.etnetera.qa.seleniumbrowser.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

public class BrowserContextWait extends FluentWait<BrowserContext> {

	protected BrowserContext context;

	public BrowserContextWait(BrowserContext context, Clock clock, Sleeper sleeper, double timeout, double retryInterval) {
		super(context, clock, sleeper);
		withTimeout((long) (timeout * 1000), TimeUnit.MILLISECONDS);
		pollingEvery((long) (retryInterval * 1000), TimeUnit.MILLISECONDS);
		ignoring(NotFoundException.class);
		this.context = context;
	}

	public BrowserContextWait(BrowserContext context, double timeout, double retryInterval) {
		this(context, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeout, retryInterval);
	}

	public BrowserContextWait(BrowserContext context, double timeout) {
		this(context, timeout, context.getWaitRetryInterval());
	}

	public BrowserContextWait(BrowserContext context) {
		this(context, context.getWaitTimeout());
	}

	@Override
	protected RuntimeException timeoutException(String message, Throwable lastException) {
		TimeoutException ex = new TimeoutException(message, lastException);
		ex.addInfo(WebDriverException.DRIVER_INFO, context.getDriver().getClass().getName());
		if (context.getDriver() instanceof RemoteWebDriver) {
			RemoteWebDriver remote = (RemoteWebDriver) context.getDriver();
			if (remote.getSessionId() != null) {
				ex.addInfo(WebDriverException.SESSION_ID, remote.getSessionId().toString());
			}
			if (remote.getCapabilities() != null) {
				ex.addInfo("Capabilities", remote.getCapabilities().toString());
			}
		}
		throw ex;
	}

}
