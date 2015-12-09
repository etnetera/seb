package com.etnetera.qa.seleniumbrowser.browser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;
import com.etnetera.qa.seleniumbrowser.listener.EventConstructException;
import com.etnetera.qa.seleniumbrowser.listener.EventFiringBrowserBridgeListener;
import com.etnetera.qa.seleniumbrowser.page.Page;
import com.etnetera.qa.seleniumbrowser.page.PageManager;

public class Browser implements BrowserContext {
	
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
		
		EventFiringWebDriver driver = new EventFiringWebDriver(configuration.getDriver());
		driver.register(new EventFiringBrowserBridgeListener(this));
		this.driver = driver;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BrowserConfiguration getConfiguration() {
		return configuration;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getConfiguration(Class<T> configuration) {
		return (T) getConfiguration();
	}

	public WebDriver getDriver() {
		return driver;
	}
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseUrlRegex() {
		return baseUrlRegex;
	}

	public void setBaseUrlRegex(String baseUrlRegex) {
		this.baseUrlRegex = baseUrlRegex;
	}

	public boolean isUrlVerification() {
		return urlVerification;
	}

	public void setUrlVerification(boolean urlVerification) {
		this.urlVerification = urlVerification;
	}

	@Override
	public double getWaitTimeout() {
		return waitTimeout;
	}

	public void setWaitTimeout(double waitTimeout) {
		this.waitTimeout = waitTimeout;
	}

	@Override
	public double getWaitRetryInterval() {
		return waitRetryInterval;
	}

	public void setWaitRetryInterval(double waitRetryInterval) {
		this.waitRetryInterval = waitRetryInterval;
	}
	
	public boolean isReported() {
		return reported;
	}

	public void setReported(boolean reported) {
		this.reported = reported;
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
	public Page getPage() {
		return page;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Object> T getPage(Class<T> page) {
		return (T) this.page;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Object> T getPage(T page) {
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
	public boolean verifyAt(Class<? extends Page> page) {
		return PageManager.verifyAt(page, this);
	}
	
	@Override
	public boolean verifyAt(Page page) {
		return PageManager.verifyAt(page);
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
	public Page initOnePage(Object firstPage, Object ... anotherPages) {
		return PageManager.initOne(this, firstPage, anotherPages);
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
	public void getUrl(String url) {
		driver.get(url);
	}

	public void quit() {
		driver.quit();
	}
	
	public void useEnclosingMethodLabel() {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
	    final String s = e.getClassName();
	    label = s.substring(s.lastIndexOf('.') + 1, s.length()) + "-" + e.getMethodName();
	}
	
	public void report(BrowserContext context, String label) {
		triggerEvent(constructEvent(OnReportEvent.class, context).with(label));
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <T extends BrowserEvent> T constructEvent(Class<T> eventCls, BrowserContext context) {
		try {
			return (T) eventCls.getConstructor().newInstance().with(context, LocalDateTime.now());
		} catch (Exception e) {
			throw new EventConstructException("Unable to construct event " + eventCls.getName(), e);
		}
	}
	
	@Override
	public void triggerEvent(BrowserEvent event) {
		event.init();
		listeners.forEach(l -> event.notify(l));
	}
	
	public void saveFile(String content, String name, String extension) {
		saveFile(content.getBytes(), name, extension);
	}
	
	public void saveFile(byte[] bytes, String name, String extension) {
		if (!reported) return;
		try {
			Files.write(getUniqueFilePath(name, extension), bytes);
		} catch (IOException e) {
			throw new BrowserException("Unable to save file " + name, e);
		}
	}
	
	public void saveFile(File file, String name, String extension) {
		if (!reported) return;
		try {
			Files.copy(file.toPath(), getUniqueFilePath(name, extension));
		} catch (IOException e) {
			throw new BrowserException("Unable to save file " + name, e);
		}
	}
	
	public Path getFilePath(String name, String extension) {
		return reportDir.toPath().resolve(BrowserUtils.join(".", name, extension));
	}
	
	public Path getUniqueFilePath(String name, String extension) {
		Path path = getFilePath(name, extension);
		int suffix = 0;
		while (path.toFile().exists()) {
			path = getFilePath(BrowserUtils.join("-", name, ++suffix), extension);
		}
		return path;
	}
	
}
