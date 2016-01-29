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
package cz.etnetera.seb.logic;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.SebContext;

/**
 * Basic logic.
 */
abstract public class Logic implements SebContext {
	
	protected SebContext context;

	public Logic with(SebContext context) {
		this.context = context;
		return this;
	}
	
	@Override
	public SebContext getContext() {
		return context;
	}
	
	@Override
	public Seb getSeb() {
		return context.getSeb();
	}
	
	public Logic init() {
		// do whatever you want
		return this;
	}

	@Override
	public List<WebElement> findElements(By by) {
		return context.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return context.findElement(by);
	}
	
}
