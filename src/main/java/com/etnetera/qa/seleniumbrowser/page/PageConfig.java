package com.etnetera.qa.seleniumbrowser.page;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows configure page object directly instead
 * of tweaking its properties and methods.
 * 
 * @author zdenek
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageConfig {

	public String[] uri() default {};
	
	public String[] uriRegex() default {};
	
	public String[] baseUrl() default {};
	
	public String[] baseUrlRegex() default {};
	
	public boolean[] urlVerification() default {};
	
	public double[] waitTimeout() default {};
	
	public double[] waitRetryInterval() default {};
	
	public double[] waitBeforePageInitTimeout() default {};
	
}
