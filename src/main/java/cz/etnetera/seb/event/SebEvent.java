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
package cz.etnetera.seb.event;

import java.io.File;
import java.time.LocalDateTime;

import org.openqa.selenium.WebDriver;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.SebContext;
import cz.etnetera.seb.listener.SebListener;

abstract public class SebEvent {
	
	protected SebContext context;
	
	protected LocalDateTime time;
	
	protected String sebLabel;
	
	protected String label;
	
	protected String filePrefix;
	
	public SebEvent with(SebContext context, LocalDateTime time) {
		this.context = context;
		this.time = time;
		return this;
	}

	abstract public void notify(SebListener listener);
	
	public void notifyEnabled(SebListener listener) {
		if (listener.isEnabled(this)) {
			notify(listener);
		}
	}
	
	/**
	 * Is called before event is triggered.
	 */
	public void init() {
		sebLabel = context.getSeb().getLabel();
		label = generateLabel();
		filePrefix = context.getUtils().join(Seb.LABEL_DELIMITER, time.format(Seb.FILE_DATE_FORMATTER), sebLabel, context.getClass().getSimpleName(), label);  
	}
	
	public SebContext getContext() {
		return context;
	}

	public LocalDateTime getTime() {
		return time;
	}
	
	public Seb getSeb() {
		return context.getSeb();
	}
	
	public <T> T getSeb(Class<T> seb) {
		return context.getSeb(seb);
	}
	
	public WebDriver getDriver() {
		return context.getDriver();
	}
	
	public <T> T getDriver(Class<T> driver) {
		return context.getDriver(driver);
	}
	
	public void saveFile(String content, String name, String extension) {
		context.saveFile(content, getEventFileName(name), extension);
	}
	
	public void saveFile(byte[] bytes, String name, String extension) {
		context.saveFile(bytes, getEventFileName(name), extension);
	}
	
	public void saveFile(File file, String name, String extension) {
		context.saveFile(file, getEventFileName(name), extension);
	}
	
	protected String getEventFileName(String name) {
		return context.getUtils().join(Seb.LABEL_DELIMITER, filePrefix, name);
	}
	
	protected String generateLabel() {
		String name = getClass().getSimpleName();
		String endTrim = "Event";
		return name.endsWith(endTrim) ? name.substring(0, name.length() - endTrim.length()) : name;
	}
	
}
