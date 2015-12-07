package com.etnetera.qa.seleniumbrowser.element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Should be used for element which are not required
 * when using PageFactory. If they are not present MissingWebElement
 * is instantiated instead.
 * 
 * @author zdenek
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OptionalElement {

}
