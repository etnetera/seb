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
package cz.etnetera.seleniumbrowser.event.impl;

import cz.etnetera.seleniumbrowser.listener.BrowserListener;
import cz.etnetera.seleniumbrowser.module.Module;

public class OnModuleInitExceptionEvent extends OnExceptionEvent {
	
	protected Module module;

	public OnModuleInitExceptionEvent with(Module module, Throwable throwable) {
		this.module = module;
		super.with(throwable);
		return this;
	}
	
	@Override
	public void notify(BrowserListener listener) {
		listener.onException(this);
	}
	
	public Module getModule() {
		return module;
	}
	
}
