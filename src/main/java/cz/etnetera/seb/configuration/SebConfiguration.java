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
package cz.etnetera.seb.configuration;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.SebContext;
import cz.etnetera.seb.listener.SebListener;
import cz.etnetera.seb.listener.impl.ConfigListener;

/**
 * Interface for Seb configuration.
 * You are free to add whatever methods you want
 * as you can easily get overridden configuration
 * {@link SebContext#getConfiguration(Class)}.
 */
public interface SebConfiguration {
	
	/**
	 * It is called after putting it into Seb.
	 */
	void init();
	
	/**
	 * Base URL for pages.
	 * 
	 * @return The base URL
	 */
	String getBaseUrl();
	
	/**
	 * Base URL regex for pages.
	 * 
	 * @return The base URL regex
	 */
	String getBaseUrlRegex();
	
	/**
	 * Is URL on pages verified?
	 * 
	 * @return Verification status
	 */
	boolean isUrlVerification();
	
	/**
	 * Hub URL for RemoteWebDriver.
	 * 
	 * @return The hub URL
	 */
	String getHubUrl();
	
	/**
	 * Returns {@link WebDriver} instance.
	 * 
	 * @param caps The non null desired capabilities.
	 * @return The driver
	 */
	WebDriver getDriver(DesiredCapabilities caps);
	
	/**
	 * Returns optional {@link DesiredCapabilities}.
	 * 
	 * @return The desired capabilities.
	 */
	DesiredCapabilities getCapabilities();
	
	/**
	 * Returns wait timeout.
	 * 
	 * @return The wait timeout.
	 */
	double getWaitTimeout();
	
	/**
	 * Returns wait retry interval.
	 * 
	 * @return The wait retry interval.
	 */
	double getWaitRetryInterval();
	
	/**
	 * Returns wait before page initialization timeout.
	 * 
	 * @return The wait before page initialization timeout.
	 */
	double getWaitBeforePageInitTimeout();
	
	/**
	 * Is storing files using Seb enabled?
	 * 
	 * @return Reporting status.
	 */
	boolean isReported();
	
	/**
	 * Returns directory for storing Seb report directories.
	 * 
	 * @return The reports root directory.
	 */
	File getReportsRootDir();
	
	/**
	 * Returns directory for storing Seb report files.
	 * If null, unique directory is created inside
	 * reports root directory.
	 * 
	 * @return The report directory.
	 */
	File getReportDir();
	
	/**
	 * Returns list of Seb event listeners.
	 * 
	 * @return The Seb event listeners.
	 */
	List<SebListener> getListeners();
	
	/**
	 * Is switching to alerts supported.
	 * If you are using PhantomJS driver, modify this check.
	 * 
	 * @param driver The driver
	 * @return Support alert status
	 */
	boolean isAlertSupported(WebDriver driver);
	
	/**
	 * Is driver created on first need.
	 * Return true if driver will be created before first need
	 * false for creating it immediately after {@link Seb} is constructed.   
	 * 
	 * @return Is driver created on first need
	 */
	boolean isLazyDriver();
	
	/**
	 * Basic log level.
	 * 
	 * @return The log level
	 */
	Level getLogLevel();
	
	/**
	 * Is used for logging through {@link ConfigListener}.
	 * It should represent configuration keys and values as they
	 * will be printed into properties file.
	 * 
	 * @return Map with configuration values
	 */
	Map<Object, Object> asMap();
	
}
