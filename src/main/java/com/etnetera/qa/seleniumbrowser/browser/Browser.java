package com.etnetera.qa.seleniumbrowser.browser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import com.etnetera.qa.seleniumbrowser.configuration.BasicBrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.configuration.BrowserConfiguration;
import com.etnetera.qa.seleniumbrowser.configuration.BrowserConfigurationConstructException;
import com.etnetera.qa.seleniumbrowser.context.VerificationException;
import com.etnetera.qa.seleniumbrowser.element.ElementFieldDecorator;
import com.etnetera.qa.seleniumbrowser.element.ElementLoader;
import com.etnetera.qa.seleniumbrowser.element.MissingElement;
import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeDriverConstructEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;
import com.etnetera.qa.seleniumbrowser.listener.EventConstructException;
import com.etnetera.qa.seleniumbrowser.listener.EventFiringBrowserBridgeListener;
import com.etnetera.qa.seleniumbrowser.logic.Logic;
import com.etnetera.qa.seleniumbrowser.logic.LogicConstructException;
import com.etnetera.qa.seleniumbrowser.module.Module;
import com.etnetera.qa.seleniumbrowser.module.ModuleConstructException;
import com.etnetera.qa.seleniumbrowser.page.Page;
import com.etnetera.qa.seleniumbrowser.page.PageConstructException;
import com.etnetera.qa.seleniumbrowser.source.DataSource;
import com.etnetera.qa.seleniumbrowser.source.PropertySource;
import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

/**
 * Wrapper class for {@link WebDriver}. It is configured using
 * {@link BrowserConfiguration}. Controls automatic reporting using listeners
 * provided by configuration.
 */
public class Browser implements BrowserContext {

	public static final String PROPERTIES_CONFIGURATION_KEY = "properties";
	
	public static final String DEFAULT_CONFIGURATION_KEY = "default";
	
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
	
	protected Map<String, Object> dataHolder = new HashMap<String, Object>();
	
	protected BrowserUtils utils = new BrowserUtils();
	
	protected ElementLoader elementLoader = new ElementLoader(this);
	
	protected JavascriptLibrary javascriptLibrary = new JavascriptLibrary();
	
	/**
	 * Constructs a new instance with default configuration. It constructs
	 * {@link BrowserConfiguration} with system properties using {@link System#getProperties()}
	 * and properties from resource named seleniumbrowser.properties.
	 */
	public Browser() {
		initDefault();
	}
	
	/**
	 * Constructs a new instance with configuration constructed
	 * from given class. Configuration class needs to have
	 * constructor with no parameters.
	 */
	public <T extends BrowserConfiguration> Browser(Class<T> configCls) {
		init(configCls);
	}

	/**
	 * Constructs a new instance and configures it.
	 * 
	 * @param configuration
	 *            The configuration
	 */
	public Browser(BrowserConfiguration configuration) {
		init(configuration);
	}
	
	protected void initDefault() {
		init(BasicBrowserConfiguration.class);
	}
	
	protected <T extends BrowserConfiguration> void init(Class<T> configCls) {
		try {
			init(configCls.getConstructor().newInstance());
		} catch (Exception e) {
			throw new BrowserConfigurationConstructException("Unable to construct browser configuration " + configCls.getName(), e);
		}
	}
	
	protected void init(BrowserConfiguration configuration) {
		applyConfiguration(configuration);
		initListeners();
		driver = createDriver();
	}
	
	protected void applyConfiguration(BrowserConfiguration configuration) {
		configuration.init();
		this.configuration = configuration;
		baseUrl = configuration.getBaseUrl();
		baseUrlRegex = configuration.getBaseUrlRegex();
		urlVerification = configuration.isUrlVerification();
		waitTimeout = configuration.getWaitTimeout();
		waitRetryInterval = configuration.getWaitRetryInterval();
		reported = configuration.isReported();
		reportDir = configuration.getReportDir();
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
		if (configuration instanceof DataSource)
			dataHolder = ((DataSource) configuration).getDataHolder();

		listeners = configuration.getListeners();
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
	}
	
	protected void initListeners() {
		if (listeners != null)
			listeners.forEach(l -> l.init(this));
	}
	
	protected WebDriver createDriver() {
		// collect capabilities
		DesiredCapabilities caps = configuration.getCapabilities();
		// notify listeners to allow its change
		BeforeDriverConstructEvent befDriverConstEvent = constructEvent(BeforeDriverConstructEvent.class).with(caps);
		triggerEvent(befDriverConstEvent);

		EventFiringWebDriver drv = new EventFiringWebDriver(
				configuration.getDriver(befDriverConstEvent.getCapabilities()));
		drv.register(new EventFiringBrowserBridgeListener(this));
		return drv;
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
	 * @param labels
	 *            Browser labels
	 */
	public void setLabel(String... labels) {
		this.label = utils.join(LABEL_DELIMITER, (Object[]) labels);
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
	 * @param reported The reported status.
	 */
	public void setReported(boolean reported) {
		this.reported = reported;
	}

	/**
	 * Returns utils instance.
	 * 
	 * @return The utils instance
	 */
	@Override
	public BrowserUtils getUtils() {
		return utils;
	}

	public ElementLoader getElementLoader() {
		return elementLoader;
	}

	public JavascriptLibrary getJavascriptLibrary() {
		return javascriptLibrary;
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
	 * @return The event instance
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
	public void assertAt(Class<?> page) {
		if (!isAt(page))
			throw new AssertionError("Page " + page + " is not assignable to actual page "
					+ (this.page == null ? null : this.page.getClass()));
	}

	@Override
	public <T extends Page> T goToSafely(Class<T> page) {
		try {
			return goTo(page);
		} catch (VerificationException e) {
			return null;
		}
	}

	@Override
	public <T extends Page> T goToSafely(T page) {
		try {
			return goTo(page);
		} catch (VerificationException e) {
			return null;
		}
	}

	@Override
	public <T extends Page> T initPageSafely(Class<T> page) {
		try {
			return initPage(page);
		} catch (VerificationException e) {
			return null;
		}
	}

	@Override
	public <T extends Page> T initPageSafely(T page) {
		try {
			return initPage(page);
		} catch (VerificationException e) {
			return null;
		}
	}

	@Override
	public Page initOnePageSafely(Object... pages) {
		try {
			return initOnePage(pages);
		} catch (VerificationException e) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Page> T goTo(Class<T> page) {
		return (T) constructPage(page).goTo();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Page> T goTo(T page) {
		return (T) page.goTo();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Page> T initPage(Class<T> page) {
		return (T) constructPage(page).init();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Page> T initPage(T page) {
		return (T) page.init();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page initOnePage(Object... pages) {
		Page verifiedPage = null;
		for (Object page : pages) {
			if (page instanceof Page) {
				verifiedPage = initPageSafely((Page) page);
			} else {
				verifiedPage = initPageSafely((Class<? extends Page>) page);
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
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Page> T constructPage(Class<T> page) {
		try {
			Constructor<T> ctor = page.getConstructor();
			return (T) ctor.newInstance().with(this);
		} catch (Exception e) {
			throw new PageConstructException("Unable to construct page " + page.getName(), e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Module> T initModule(T module) {
		return (T) module.init();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Module> T initModule(Class<T> module, BrowserContext context, WebElement element) {
		return (T) constructModule(module, context, element).init();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Module> T constructModule(Class<T> module, BrowserContext context, WebElement element) {
		try {
			Constructor<T> ctor = module.getConstructor();
			return (T) ctor.newInstance().with(context, element);
		} catch (Exception e) {
			throw new ModuleConstructException("Unable to construct module " + module.getName(), e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Logic> T initLogic(T logic) {
		return (T) logic.init();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Logic> T initLogic(Class<T> logic, BrowserContext context) {
		return (T) constructLogic(logic, context).init();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Logic> T constructLogic(Class<T> logic, BrowserContext context) {
		try {
			Constructor<T> ctor = logic.getConstructor();
			return (T) ctor.newInstance().with(context);
		} catch (Exception e) {
			throw new LogicConstructException("Unable to construct logic " + logic.getName(), e);
		}
	}
	
	public void initElements(BrowserContext context) {
		PageFactory.initElements(
				new ElementFieldDecorator(new DefaultElementLocatorFactory(context), context),
				context);
	}
	
	@Override
	public boolean isPresent(WebElement element) {
		return !isNotPresent(element);
	}
	
	@Override
	public boolean isNotPresent(WebElement element) {
		return element == null || element instanceof MissingElement || (element instanceof Module && ((Module) element).isNotPresent());
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
	public <T extends WebElement> T findElement(SearchContext context, By by, Class<T> elementCls, boolean optional) {
		return elementLoader.findElement(context, by, elementCls, optional);
	}
	
	@Override
	public <T extends WebElement> List<T> findElements(SearchContext context, By by, Class<T> elementCls) {
		return elementLoader.findElements(context, by, elementCls);
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
	public Map<String, Object> getDataHolder() {
		return dataHolder;
	}

	@Override
	public String getProperty(String key) {
		return configuration instanceof PropertySource ? ((PropertySource) configuration).getProperty(key) : null;
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
		return reportDir.toPath().resolve(utils.join(".", name, extension));
	}

	protected Path getUniqueFilePath(String name, String extension) {
		name = escapeFileName(name);
		Path path = getFilePath(name, extension);
		int suffix = 0;
		while (path.toFile().exists()) {
			path = getFilePath(utils.join(LABEL_DELIMITER, name, ++suffix), extension);
		}
		return path;
	}

	protected String escapeFileName(String name) {
		return name.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
	}

}
