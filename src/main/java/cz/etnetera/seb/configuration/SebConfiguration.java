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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import cz.etnetera.seb.SebContext;
import cz.etnetera.seb.listener.SebListener;

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
	 * Is storing files using Seb enabled?
	 * 
	 * @return Reporting status.
	 */
	boolean isReported();
	
	/**
	 * Returns directory for storing Seb files.
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
	
}
