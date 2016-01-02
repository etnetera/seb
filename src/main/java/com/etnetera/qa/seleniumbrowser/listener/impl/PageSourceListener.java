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
package com.etnetera.qa.seleniumbrowser.listener.impl;

import com.etnetera.qa.seleniumbrowser.browser.Browser;
import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.AfterPageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforeBrowserQuitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.BeforePageInitEvent;
import com.etnetera.qa.seleniumbrowser.event.impl.OnReportEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class PageSourceListener extends BrowserListener {
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Browser browser) {
		super.init(browser);
		// enable this listener on report and before browser quit as default
		enable(OnReportEvent.class, BeforeBrowserQuitEvent.class);
	}
	
	@Override
	public void beforePageInit(BeforePageInitEvent event) {
		savePageSource(event);
	}
	
	@Override
	public void afterPageInit(AfterPageInitEvent event) {
		savePageSource(event);
	}

	@Override
	public void beforeBrowserQuit(BeforeBrowserQuitEvent event) {
		savePageSource(event);
	}
	
	@Override
	public void onReport(OnReportEvent event) {
		savePageSource(event);
	}

	protected void savePageSource(BrowserEvent event) {
		if (event.getBrowser().isReported())
			saveFile(event, event.getDriver().getPageSource(), null, "html");
	}

}
