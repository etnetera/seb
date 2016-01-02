/* Copyright 2016 Etnetera
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
package cz.etnetera.seleniumbrowser.configuration;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import cz.etnetera.seleniumbrowser.browser.BrowserContext;
import cz.etnetera.seleniumbrowser.listener.BrowserListener;

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
