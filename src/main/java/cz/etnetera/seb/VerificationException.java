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
package cz.etnetera.seb;

import cz.etnetera.seb.module.Module;
import cz.etnetera.seb.page.Page;

/**
 * Exception thrown from {@link Page} or {@link Module} 
 * on failed verification.
 */
public class VerificationException extends SebException {

	private static final long serialVersionUID = 911749792790433730L;

	public VerificationException() {
		super();
	}

	public VerificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public VerificationException(String message) {
		super(message);
	}

	public VerificationException(Throwable cause) {
		super(cause);
	}

}
