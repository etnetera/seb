package com.etnetera.qa.seleniumbrowser.element;

import java.util.AbstractList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.etnetera.qa.seleniumbrowser.browser.BrowserContext;

public class BrowserElementList extends AbstractList<BrowserElement> {

	protected BrowserContext context;
	
	protected List<WebElement> webElements;
	
	protected Class<? extends BrowserElement> elementCls;
	
	public BrowserElementList(BrowserContext context, List<WebElement> webElements,
			Class<? extends BrowserElement> elementCls) {
		this.context = context;
		this.webElements = webElements;
		this.elementCls = elementCls;
	}

	@Override
	public BrowserElement get(int index) {
		return context.initBrowserElement(elementCls, webElements.get(index), false);
	}

	@Override
	public int size() {
		return webElements.size();
	}

}
