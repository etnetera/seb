package com.etnetera.qa.seleniumbrowser.element;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * Is used when decorated field is not present on page while
 * using PageFactory and field is annotated with OptionalWebElement.
 * 
 * @author zdenek
 * 
 */
public class MissingElement implements WebElement {

	private final NoSuchElementException nsee;
	
	public MissingElement() {
		this.nsee = new NoSuchElementException("Missing element constructed");
	}
	
	public MissingElement(NoSuchElementException nsee) {
		this.nsee = nsee;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		throw nsee;
	}

	@Override
	public void click() {
		throw nsee;
	}

	@Override
	public void submit() {
		throw nsee;
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		throw nsee;
	}

	@Override
	public void clear() {
		throw nsee;
	}

	@Override
	public String getTagName() {
		throw nsee;
	}

	@Override
	public String getAttribute(String name) {
		throw nsee;
	}

	@Override
	public boolean isSelected() {
		throw nsee;
	}

	@Override
	public boolean isEnabled() {
		throw nsee;
	}

	@Override
	public String getText() {
		throw nsee;
	}

	@Override
	public List<WebElement> findElements(By by) {
		throw nsee;
	}

	@Override
	public WebElement findElement(By by) {
		throw nsee;
	}

	@Override
	public boolean isDisplayed() {
		throw nsee;
	}

	@Override
	public Point getLocation() {
		throw nsee;
	}

	@Override
	public Dimension getSize() {
		throw nsee;
	}

	@Override
	public String getCssValue(String propertyName) {
		throw nsee;
	}

}
