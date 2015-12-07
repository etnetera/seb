package com.etnetera.qa.seleniumbrowser.element;

import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.module.Module;

public class ElementManager {

	public static boolean isPresent(WebElement element) {
		return !isNotPresent(element);
	}
	
	public static boolean isNotPresent(WebElement element) {
		return element == null || element instanceof MissingElement || (element instanceof Module && ((Module) element).isNotPresent());
	}
	
}
