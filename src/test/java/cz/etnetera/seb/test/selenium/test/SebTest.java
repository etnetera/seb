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
package cz.etnetera.seb.test.selenium.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.test.selenium.configuration.SebConfig;

abstract public class SebTest {

	@Rule public TestName name = new TestName();
	
	protected Seb seb;
	
	@Before
	public void before() {
		seb = new Seb(SebConfig.class).withLabel(getClass().getSimpleName(), name.getMethodName()).start();
	}
	
	@After
	public void after() {
		if (seb != null) seb.quit();
	}

}
