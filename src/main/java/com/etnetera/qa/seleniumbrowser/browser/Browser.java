package com.etnetera.qa.seleniumbrowser.browser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.etnetera.qa.seleniumbrowser.configuration.BrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.configuration.ChainedBrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.configuration.DefaultBrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.configuration.PropertiesBrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeDriverConstructEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;
import com.etnetera.qa.seleniumbrowser.listener.EventConstructException;
import com.etnetera.qa.seleniumbrowser.listener.EventFiringBrowserBridgeListener;
import com.etnetera.qa.seleniumbrowser.page.Page;
import com.etnetera.qa.seleniumbrowser.page.PageManager;

/**
 * Wrapper class for {@link WebDriver}. It is configured using
 * {@link BrowserConfiguration}. Controls automatic reporting using listeners
 * provided by configuration.
 */
public class Browser implements BrowserContext {

	public static final String LABEL_DELIMITER = "-";

	public static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	protected BrowserConfiguration configuration;

	protected WebDriver driver;

	protected String baseUrl;

	protected String baseUrlRegex;

	protected boolean urlVerification;

	protected double waitTimeout;

	protected double waitRetryInterval;

	protected Page page;

	protected String label;

	protected boolean reported;

	protected File reportDir;

	protected List<BrowserListener> listeners;

	/**
	 * Constructs a new instance with default configuration. It uses
	 * {@link System#getProperties()}, resource named seleniumbrowser.properties
	 * and {@link DefaultBrowserConfiguration} in this order.
	 */
	public Browser() {
		this(new ChainedBrowserConfiguration().addConfiguration(
				new PropertiesBrowserConfiguration().addProperties(System.getProperties()).addDefaultProperties())
				.addConfiguration(new DefaultBrowserConfiguration()));
	}

	/**
	 * Constructs a new instance and configures it.
	 * 
	 * @param configuration
	 *            The configuration
	 */
	public Browser(BrowserConfiguration configuration) {
		this.configuration = configuration;
		this.baseUrl = configuration.getBaseUrl();
		this.baseUrlRegex = configuration.getBaseUrlRegex();
		this.urlVerification = configuration.isUrlVerification();
		this.waitTimeout = configuration.getWaitTimeout();
		this.waitRetryInterval = configuration.getWaitRetryInterval();
		this.reported = configuration.isReported();
		this.reportDir = configuration.getReportDir();
		if (reported) {
			if (reportDir == null) {
				throw new BrowserException("Report directory is null");
			}
			if (!reportDir.exists()) {
				try {
					Files.createDirectories(reportDir.toPath());
				} catch (IOException e) {
					throw new BrowserException("Report directory does not exists and can not be created " + reportDir);
				}
			} else if (!reportDir.isDirectory()) {
				throw new BrowserException("Report directory is not directory " + reportDir);
			} else if (!reportDir.canWrite()) {
				throw new BrowserException("Report directory is not writable " + reportDir);
			}
		}

		this.listeners = configuration.getListeners();
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.forEach(l -> l.init());

		// collect capabilities
		DesiredCapabilities caps = configuration.getCapabilities();
		// notify listeners to allow its change
		BeforeDriverConstructEvent befDriverConstEvent = constructEvent(BeforeDriverConstructEvent.class).with(caps);
		triggerEvent(befDriverConstEvent);

		EventFiringWebDriver driver = new EventFiringWebDriver(
				configuration.getDriver(befDriverConstEvent.getCapabilities()));
		driver.register(new EventFiringBrowserBridgeListener(this));
		this.driver = driver;
	}

	/**
	 * Browser label which is mainly used for reporting.
	 * 
	 * @return Browser label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Modify browser label
	 * 
	 * @param label
	 *            Browser label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Modify browser label joining given labels.
	 * 
	 * @param label
	 *            Browser label
	 */
	public void setLabel(String... labels) {
		this.label = BrowserUtils.join(LABEL_DELIMITER, (Object[]) labels);
	}

	/**
	 * Base URL for pages.
	 * 
	 * @return The base URL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Updates base URL
	 * 
	 * @param baseUrl
	 *            New base URL
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Base URL regex for pages.
	 * 
	 * @return The base URL regex
	 */
	public String getBaseUrlRegex() {
		return baseUrlRegex;
	}

	/**
	 * Updates base URL regex
	 * 
	 * @param baseUrlRegex
	 *            New base URL regex
	 */
	public void setBaseUrlRegex(String baseUrlRegex) {
		this.baseUrlRegex = baseUrlRegex;
	}

	/**
	 * Is URL on pages verified?
	 * 
	 * @return Verification status
	 */
	public boolean isUrlVerification() {
		return urlVerification;
	}

	/**
	 * Toggles pages URL verification.
	 * 
	 * @param urlVerification
	 *            The new status
	 */
	public void setUrlVerification(boolean urlVerification) {
		this.urlVerification = urlVerification;
	}

	@Override
	public double getWaitTimeout() {
		return waitTimeout;
	}

	/**
	 * Sets default wait timeout.
	 * 
	 * @param waitTimeout
	 *            The default wait timeout.
	 */
	public void setWaitTimeout(double waitTimeout) {
		this.waitTimeout = waitTimeout;
	}

	@Override
	public double getWaitRetryInterval() {
		return waitRetryInterval;
	}

	/**
	 * Sets default wait retry interval.
	 * 
	 * @param waitRetryInterval
	 *            The default wait retry interval.
	 */
	public void setWaitRetryInterval(double waitRetryInterval) {
		this.waitRetryInterval = waitRetryInterval;
	}

	/**
	 * Is storing files using browser enabled.
	 * 
	 * @return Reporting status.
	 */
	public boolean isReported() {
		return reported;
	}

	/**
	 * Toggles storing files using browser.
	 * 
	 * @param reported
	 */
	public void setReported(boolean reported) {
		this.reported = reported;
	}

	/**
	 * Quits browser and wrapped {@link WebDriver}.
	 */
	public void quit() {
		triggerEvent(constructEvent(BeforeBrowserQuitEvent.class));
		driver.quit();
		triggerEvent(constructEvent(AfterBrowserQuitEvent.class));
	}

	/**
	 * Sets label using enclosing method class name and method name.
	 */
	public void useEnclosingMethodLabel() {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		final String s = e.getClassName();
		setLabel(s.substring(s.lastIndexOf('.') + 1, s.length()), e.getMethodName());
	}

	/**
	 * Triggers {@link OnReportEvent} with given context and label.
	 * 
	 * @param context
	 *            The report context
	 * @param label
	 *            The report label
	 */
	public void report(BrowserContext context, String label) {
		triggerEvent(constructEvent(OnReportEvent.class, context).with(label));
	}

	/**
	 * Constructs a new instance of {@link BrowserEvent} subclass with given
	 * context and local time.
	 * 
	 * @param eventCls
	 *            The event class to construct
	 * @param context
	 *            The context to use
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T extends BrowserEvent> T constructEvent(Class<T> eventCls, BrowserContext context) {
		try {
			return (T) eventCls.getConstructor().newInstance().with(context, LocalDateTime.now());
		} catch (Exception e) {
			throw new EventConstructException("Unable to construct event " + eventCls.getName(), e);
		}
	}

	@Override
	public BrowserContext getContext() {
		return this;
	}

	@Override
	public Browser getBrowser() {
		return this;
	}

	@Override
	public BrowserConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getConfiguration(Class<T> configuration) {
		return (T) this.configuration;
	}

	@Override
	public WebDriver getDriver() {
		return driver;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getDriver(Class<T> driver) {
		return (T) this.driver;
	}

	@Override
	public Page getPage() {
		return page;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getPage(Class<T> page) {
		return (T) this.page;
	}

	@Override
	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public boolean isAt(Class<?> page) {
		return this.page != null && page.isAssignableFrom(this.page.getClass());
	}

	@Override
	public boolean isAt(Object page) {
		return this.page != null && page.getClass().isAssignableFrom(this.page.getClass());
	}

	@Override
	public void assertAt(Class<?> page) {
		if (!isAt(page))
			throw new AssertionError("Page " + page + " is not assignable to actual page "
					+ (this.page == null ? null : this.page.getClass()));
	}

	@Override
	public void assertAt(Object page) {
		if (!isAt(page))
			throw new AssertionError("Page " + page.getClass() + " is not assignable to actual page "
					+ (this.page == null ? null : this.page.getClass()));
	}

	@Override
	public <T extends Page> T goToSafely(Class<T> page) {
		return PageManager.goToSafely(page, this);
	}

	@Override
	public <T extends Page> T goToSafely(T page) {
		return PageManager.goToSafely(page);
	}

	@Override
	public <T extends Page> T initPageSafely(Class<T> page) {
		return PageManager.initSafely(page, this);
	}

	@Override
	public <T extends Page> T initPageSafely(T page) {
		return PageManager.initSafely(page);
	}

	@Override
	public Page initOnePageSafely(Object... pages) {
		return PageManager.initOneSafely(this, pages);
	}

	@Override
	public <T extends Page> T goTo(Class<T> page) {
		return PageManager.goTo(page, this);
	}

	@Override
	public <T extends Page> T goTo(T page) {
		return PageManager.goTo(page);
	}

	@Override
	public <T extends Page> T initPage(Class<T> page) {
		return PageManager.init(page, this);
	}

	@Override
	public <T extends Page> T initPage(T page) {
		return PageManager.init(page);
	}

	@Override
	public Page initOnePage(Object... pages) {
		return PageManager.initOne(this, pages);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	@Override
	public void goToUrl(String url) {
		driver.get(url);
	}

	@Override
	public void triggerEvent(BrowserEvent event) {
		event.init();
		listeners.forEach(l -> event.notifyEnabled(l));
	}

	@Override
	public void saveFile(String content, String name, String extension) {
		saveFile(content.getBytes(), name, extension);
	}

	@Override
	public void saveFile(byte[] bytes, String name, String extension) {
		if (!reported)
			return;
		try {
			Files.write(getUniqueFilePath(name, extension), bytes);
		} catch (IOException e) {
			throw new BrowserException("Unable to save file " + name, e);
		}
	}

	@Override
	public void saveFile(File file, String name, String extension) {
		if (!reported)
			return;
		try {
			Files.copy(file.toPath(), getUniqueFilePath(name, extension));
		} catch (IOException e) {
			throw new BrowserException("Unable to save file " + name, e);
		}
	}

	protected Path getFilePath(String name, String extension) {
		return reportDir.toPath().resolve(BrowserUtils.join(".", name, extension));
	}

	protected Path getUniqueFilePath(String name, String extension) {
		name = escapeFileName(name);
		Path path = getFilePath(name, extension);
		int suffix = 0;
		while (path.toFile().exists()) {
			path = getFilePath(BrowserUtils.join(LABEL_DELIMITER, name, ++suffix), extension);
		}
		return path;
	}

	protected String escapeFileName(String name) {
		return name.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
	}

}
