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
package cz.etnetera.seb.event.impl;

import cz.etnetera.seb.event.SebEvent;

abstract public class UrlEvent extends SebEvent {

	protected String url;

	public UrlEvent with(String url) {
		this.url = url;
		return this;
	}
	
	public String getUrl() {
		return url;
	}
	
}
