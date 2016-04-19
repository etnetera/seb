package cz.etnetera.seb.listener.impl;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.etnetera.seb.event.impl.AfterDriverConstructEvent;
import cz.etnetera.seb.event.impl.AfterSebQuitEvent;
import cz.etnetera.seb.event.impl.BeforeChangeValueOfEvent;
import cz.etnetera.seb.event.impl.BeforeClickOnEvent;
import cz.etnetera.seb.event.impl.BeforeDriverConstructEvent;
import cz.etnetera.seb.event.impl.BeforeFindByEvent;
import cz.etnetera.seb.event.impl.BeforeModuleInitEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateBackEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateForwardEvent;
import cz.etnetera.seb.event.impl.BeforeNavigateToEvent;
import cz.etnetera.seb.event.impl.BeforePageInitEvent;
import cz.etnetera.seb.event.impl.BeforeScriptEvent;
import cz.etnetera.seb.event.impl.BeforeSebQuitEvent;
import cz.etnetera.seb.event.impl.OnExceptionEvent;
import cz.etnetera.seb.event.impl.OnSebStartEvent;
import cz.etnetera.seb.listener.SebListener;

public class LoggingListener extends SebListener {
	
	@Override
	public void onSebStart(OnSebStartEvent event) {
		seb.log(Level.INFO, "Seb initiated " + event.getSeb().getLabel());
	}

	@Override
	public void beforeDriverConstruct(BeforeDriverConstructEvent event) {
		seb.log(Level.INFO, "Creating driver " + event.getCapabilities());
	}
	
	@Override
	public void afterDriverConstruct(AfterDriverConstructEvent event) {
		WebDriver driver = event.getSeb().getWrappedDriver();
		String driverStr = driver.toString();
		if (driver instanceof RemoteWebDriver) {
			driverStr += " " + ((RemoteWebDriver) driver).getCapabilities();
		}
		seb.log(Level.INFO, "Driver created " + driverStr);
	}

	@Override
	public void beforeScript(BeforeScriptEvent event) {
		seb.log(Level.INFO, "Run JavaScript " + event.getScript());
	}

	@Override
	public void beforeNavigateBack(BeforeNavigateBackEvent event) {
		seb.log(Level.INFO, "Go back");
	}

	@Override
	public void beforeNavigateForward(BeforeNavigateForwardEvent event) {
		seb.log(Level.INFO, "Go forward");
	}

	@Override
	public void beforeNavigateTo(BeforeNavigateToEvent event) {
		seb.log(Level.INFO, "Open URL " + event.getUrl());
	}
	
	@Override
	public void beforeFindBy(BeforeFindByEvent event) {
		if (event.getElement() == null)
			seb.log(Level.INFO, "Find element " + event.getBy());
		else
			seb.log(Level.INFO, "Find element (from element) " + event.getBy());
	}

	@Override
	public void beforePageInit(BeforePageInitEvent event) {
		seb.log(Level.INFO, "Init page " + event.getPage());
	}

	@Override
	public void beforeModuleInit(BeforeModuleInitEvent event) {
		seb.log(Level.INFO, "Init module " + event.getModule());
	}

	@Override
	public void beforeSebQuit(BeforeSebQuitEvent event) {
		seb.log(Level.INFO, "Quiting Seb");
	}

	@Override
	public void afterSebQuit(AfterSebQuitEvent event) {
		seb.log(Level.INFO, "Seb quit");
	}

	@Override
	public void beforeClickOn(BeforeClickOnEvent event) {
		seb.log(Level.INFO, "Click");
	}

	@Override
	public void beforeChangeValueOf(BeforeChangeValueOfEvent event) {
		seb.log(Level.INFO, "Change value");
	}

	@Override
	public void onException(OnExceptionEvent event) {
		seb.log(Level.SEVERE, event.getMessage(), event.getThrowable());
	}

}
