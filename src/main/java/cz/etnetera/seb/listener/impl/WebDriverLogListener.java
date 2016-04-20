package cz.etnetera.seb.listener.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.event.impl.BeforeDriverConstructEvent;
import cz.etnetera.seb.event.impl.BeforeDriverQuitEvent;
import cz.etnetera.seb.listener.SebListener;

public class WebDriverLogListener extends SebListener {
	
	protected static final DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Seb seb) {
		super.init(seb);
		enable(BeforeDriverConstructEvent.class, BeforeDriverQuitEvent.class);
	}

	@Override
	public void beforeDriverConstruct(BeforeDriverConstructEvent event) {
		event.getCapabilities().setCapability(CapabilityType.LOGGING_PREFS, getLoggingPreferences(event));
	}

	@Override
	public void beforeDriverQuit(BeforeDriverQuitEvent event) {
		Logs logs = seb.getDriver().manage().logs();
		logs.getAvailableLogTypes().forEach(type -> storeLogs(logs, type));
	}
	
	protected LoggingPreferences getLoggingPreferences(BeforeDriverConstructEvent event) {
		Level level = seb.getLogLevel();
		LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, level);
        logPrefs.enable(LogType.CLIENT, level);
        logPrefs.enable(LogType.DRIVER, level);
        logPrefs.enable(LogType.PERFORMANCE, level);
        logPrefs.enable(LogType.PROFILER, level);
        logPrefs.enable(LogType.SERVER, level);
        return logPrefs;
	}
	
	protected void storeLogs(Logs logs, String type) {
		StringBuilder sb = new StringBuilder();
		try {
			LogEntries logEntries = logs.get(type);
	        for (LogEntry entry : logEntries) {
	            sb.append(formatLog(new Date(entry.getTimestamp()), entry.getLevel(), entry.getMessage()));
	        }
		} catch (WebDriverException e) {
			sb.append(formatLog(new Date(), Level.SEVERE, "Unable to get webdriver logs for type " + type + ".\n" + e));
		}
		String logStr = sb.toString();
		if (!logStr.isEmpty())
			seb.saveFile(logStr, "webdriver-" + type, "log");
	}
	
	protected String formatLog(Date time, Level level, String message) {
		return (LOG_DATE_FORMAT.format(time) + " " + String.format("%1$-7s", level) + " " + message).trim() + "\n";
	}

}
