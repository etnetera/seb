package com.etnetera.qa.seleniumbrowser.event.impl;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.etnetera.qa.seleniumbrowser.event.BrowserEvent;
import com.etnetera.qa.seleniumbrowser.listener.BrowserListener;

public class BeforeDriverConstructEvent extends BrowserEvent {
	
	protected DesiredCapabilities capabilities;
	
	public BeforeDriverConstructEvent with(DesiredCapabilities capapabilities) {
		this.capabilities = capapabilities;
		return this;
	}
	
	public DesiredCapabilities getCapabilities() {
		if (capabilities == null) {
			capabilities = new DesiredCapabilities();
		}
		return capabilities;
	}

	@Override
	public void notify(BrowserListener listener) {
		listener.beforeDriverConstruct(this);
	}
	
}
