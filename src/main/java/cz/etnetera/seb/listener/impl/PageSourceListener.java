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
package cz.etnetera.seb.listener.impl;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.event.SebEvent;
import cz.etnetera.seb.event.impl.AfterPageInitEvent;
import cz.etnetera.seb.event.impl.BeforeDriverQuitEvent;
import cz.etnetera.seb.event.impl.BeforePageInitEvent;
import cz.etnetera.seb.event.impl.OnReportEvent;
import cz.etnetera.seb.listener.SebListener;

public class PageSourceListener extends SebListener {
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Seb seb) {
		super.init(seb);
		// enable this listener on report and before driver quit as default
		enable(OnReportEvent.class, BeforeDriverQuitEvent.class);
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
	public void beforeDriverQuit(BeforeDriverQuitEvent event) {
		savePageSource(event);
	}
	
	@Override
	public void onReport(OnReportEvent event) {
		savePageSource(event);
	}

	protected void savePageSource(SebEvent event) {
		if (event.getSeb().isReported())
			saveFile(event, event.getDriver().getPageSource(), null, "html");
	}

}
