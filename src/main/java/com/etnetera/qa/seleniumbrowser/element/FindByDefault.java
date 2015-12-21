package com.etnetera.qa.seleniumbrowser.element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies element which is auto loaded by default
 * strategy. It means by id or name same as field name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FindByDefault {

}
