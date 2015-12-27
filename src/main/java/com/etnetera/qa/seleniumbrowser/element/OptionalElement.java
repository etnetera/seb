package com.etnetera.qa.seleniumbrowser.element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

/**
 * Identifies element which is not required to be present when using {@link PageFactory}. 
 * If they are not present {@link BrowserElement} is instantiated instead 
 * of {@link NoSuchElementException} being thrown.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OptionalElement {

}
