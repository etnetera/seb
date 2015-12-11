package com.etnetera.qa.seleniumbrowser.configuration;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

/**
 * Configures browser.
 * You are free to add whatever methods you want
 * as you can easily get overridden configuration
 * {@link BrowserContext#getConfiguration(Class)}.
 * 
 * It is better to use non primitive values as it
 * can be used later in {@link ChainedBrowserConfiguration}
 * and set its default primitive value in default
 * configuration like in {@link DefaultBrowserConfiguration};  
 */
public interface BrowserConfiguration {
	
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
	public Boolean isUrlVerification();
	
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
	 * Returns default wait timeout.
	 * 
	 * @return The default wait timeout.
	 */
	public Double getWaitTimeout();
	
	/**
	 * Returns default wait retry interval.
	 * 
	 * @return The default wait retry interval.
	 */
	public Double getWaitRetryInterval();
	
	/**
	 * Is storing files using browser enabled.
	 * 
	 * @return Reporting status.
	 */
	public Boolean isReported();
	
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
