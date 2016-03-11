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
import cz.etnetera.seb.listener.SebListener;

public class LogEvent extends SebEvent {
	
	protected Level level; 
	
	protected String message;
	
	protected Throwable throwable;

	public LogEvent with(Level level, String message) {
		return with(level, message, null);
	}
	
	public LogEvent with(Level level, Throwable throwable) {
		return with(level, null, throwable);
	}
	
	public LogEvent with(Level level, String message, Throwable throwable) {
		this.level = level;
		this.message = message;
		this.throwable = throwable;
		return this;
	}
	
	@Override
	protected void notifySpecific(SebListener listener) {
		listener.log(this);
	}
	
	public String getName() {
		return context.getClass().getName();
	}

	public Level getLevel() {
		return level;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public static enum Level {
		
		TRACE, DEBUG, INFO, WARN, ERROR
		
	}
	
}
