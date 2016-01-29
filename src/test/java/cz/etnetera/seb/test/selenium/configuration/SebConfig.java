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
package cz.etnetera.seb.test.selenium.configuration;

import cz.etnetera.seb.configuration.BasicSebConfiguration;
import cz.etnetera.seb.source.PropertiesSource;

public class SebConfig extends BasicSebConfiguration {

	public static final String PREFIX = "seb.custom.";

	public static final String USERNAME = PREFIX + "username";
	public static final String PASSWORD = PREFIX + "password";

	@Override
	public void init() {
		super.init();
		addResourcePropertiesAfter(BasicSebConfiguration.SYSTEM_PROPERTIES_KEY, "custom",
				"customProperties.properties");
		addData("custom", PropertiesSource.loadProperties("customData.properties"));
	}

	@Override
	protected String getDefaultBaseUrl() {
		return "http://www.etnetera.cz";
	}

	@Override
	protected boolean isDefaultReported() {
		return true;
	}

	public String getUsername() {
		return getProperty(USERNAME);
	}

	public String getPassword() {
		return getProperty(PASSWORD);
	}

}
