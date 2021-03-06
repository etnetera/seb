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
package cz.etnetera.seb.page;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows configure page object directly instead
 * of tweaking its properties and methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageConfig {

	String[] uri() default {};
	
	String[] uriRegex() default {};
	
	String[] baseUrl() default {};
	
	String[] baseUrlRegex() default {};
	
	String[] url() default {};
	
	String[] urlRegex() default {};
	
	boolean[] urlVerification() default {};
	
	double[] waitTimeout() default {};
	
	double[] waitRetryInterval() default {};
	
	double[] waitBeforePageInitTimeout() default {};
	
}
