package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

/**
 * Interface for browser configuration.
 * You are free to add whatever methods you want
 * as you can easily get overridden configuration
 * {@link BrowserContext#getConfiguration(Class)}.
 */
public interface BrowserConfiguration {
	
	/**
	 * It is called after putting it into browser.
	 */
	public void init();
	
	/**
	 * Base URL for pages.
	 * 
	 * @return The base URL
	 */
	public String getBaseUrl();
	
	/**
	 * Base URL regex for pages.
	 * 
	 * @return The base URL regex
	 */
	public String getBaseUrlRegex();
	
	/**
	 * Is URL on pages verified?
	 * 
	 * @return Verification status
	 */
	public boolean isUrlVerification();
	
	/**
	 * Returns {@link WebDriver} instance.
	 * 
	 * @param caps The non null desired capabilities.
	 * @return The driver
	 */
	public WebDriver getDriver(DesiredCapabilities caps);
	
	/**
	 * Returns optional {@link DesiredCapabilities}.
	 * 
	 * @return The desired capabilities.
	 */
	public DesiredCapabilities getCapabilities();
	
	/**
	 * Returns wait timeout.
	 * 
	 * @return The wait timeout.
	 */
	public double getWaitTimeout();
	
	/**
	 * Returns wait retry interval.
	 * 
	 * @return The wait retry interval.
	 */
	public double getWaitRetryInterval();
	
	/**
	 * Is storing files using browser enabled?
	 * 
	 * @return Reporting status.
	 */
	public boolean isReported();
	
	/**
	 * Returns directory for storing browser files.
	 * 
	 * @return The report directory.
	 */
	public File getReportDir();
	
	/**
	 * Returns list of browser event listeners.
	 * 
	 * @return The browser event listeners.
	 */
	public List<BrowserListener> getListeners();
	
}
