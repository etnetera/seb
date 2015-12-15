package com.etnetera.qa.seleniumbrowser.browser;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

import com.google.common.base.Supplier;

public class BrowserContextWait extends FluentWait<BrowserContext> {

	protected BrowserContext context;
	
	protected final Sleeper sleeper;
	
	protected Duration timeout;

	public BrowserContextWait(BrowserContext context, Clock clock, Sleeper sleeper, double timeout,
			double retryInterval) {
		super(context, clock, sleeper);
		withTimeout((long) (timeout * 1000), TimeUnit.MILLISECONDS);
		pollingEvery((long) (retryInterval * 1000), TimeUnit.MILLISECONDS);
		ignoring(NotFoundException.class);
		this.context = context;
		this.sleeper = sleeper;
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

	/**
	 * Until method using predicate functional interface. It solves ambiguity
	 * when using basic until method without typed parameter.
	 * 
	 * Repeatedly applies this instance's input value to the given predicate
	 * until the timeout expires or the predicate evaluates to true.
	 *
	 * @param isTrue
	 *            The predicate to wait on.
	 * @throws TimeoutException
	 *             If the timeout expires.
	 */
	public void untilTrue(Predicate<BrowserContext> isTrue) {
		super.until(new com.google.common.base.Predicate<BrowserContext>() {
			@Override
			public boolean apply(BrowserContext input) {
				return isTrue.test(input);
			}
		});
	}

	/**
	 * Until method using function functional interface. It solves ambiguity
	 * when using basic until method without typed parameter.
	 * 
	 * Repeatedly applies this instance's input value to the given function
	 * until one of the following occurs:
	 * <ol>
	 * <li>the function returns neither null nor false,</li>
	 * <li>the function throws an unignored exception,</li>
	 * <li>the timeout expires,
	 * <li>
	 * <li>the current thread is interrupted</li>
	 * </ol>
	 *
	 * @param isTrue
	 *            the parameter to pass to the {@link ExpectedCondition}
	 * @param <V>
	 *            The function's expected return type.
	 * @return The functions' return value if the function returned something
	 *         different from null or false before the timeout expired.
	 * @throws TimeoutException
	 *             If the timeout expires.
	 */
	public <V> V untilValid(Function<BrowserContext, V> isTrue) {
		return super.until(new com.google.common.base.Function<BrowserContext, V>() {
			@Override
			public V apply(BrowserContext input) {
				return isTrue.apply(input);
			}
		});
	}
	
	/**
	 * Sleeps for defined timeout without checking for any
	 * condition.
	 */
	public BrowserContextWait sleep() {
		try {
			sleeper.sleep(timeout);
			return this;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new WebDriverException(e);
		}
	}

	@Override
	public BrowserContextWait withTimeout(long duration, TimeUnit unit) {
		super.withTimeout(duration, unit);
		this.timeout = new Duration(duration, unit);
		return this;
	}

	@Override
	public BrowserContextWait withMessage(String message) {
		return (BrowserContextWait) super.withMessage(message);
	}

	@Override
	public BrowserContextWait withMessage(Supplier<String> messageSupplier) {
		return (BrowserContextWait) super.withMessage(messageSupplier);
	}

	@Override
	public BrowserContextWait pollingEvery(long duration, TimeUnit unit) {
		return (BrowserContextWait) super.pollingEvery(duration, unit);
	}

	@Override
	public <K extends Throwable> BrowserContextWait ignoreAll(Collection<Class<? extends K>> types) {
		return (BrowserContextWait) super.ignoreAll(types);
	}

	@Override
	public BrowserContextWait ignoring(Class<? extends Throwable> exceptionType) {
		return (BrowserContextWait) super.ignoring(exceptionType);
	}

	@Override
	public BrowserContextWait ignoring(Class<? extends Throwable> firstType,
			Class<? extends Throwable> secondType) {
		return (BrowserContextWait) super.ignoring(firstType, secondType);
	}

}
