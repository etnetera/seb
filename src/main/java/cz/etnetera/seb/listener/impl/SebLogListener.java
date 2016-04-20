package cz.etnetera.seb.listener.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.event.impl.AfterSebQuitEvent;
import cz.etnetera.seb.event.impl.LogEvent;
import cz.etnetera.seb.listener.SebListener;

public class SebLogListener extends SebListener {
	
	protected static final DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	
	protected List<String> logs = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Seb seb) {
		super.init(seb);
		enable(LogEvent.class, AfterSebQuitEvent.class);
	}

	@Override
	public void log(LogEvent event) {
		if (event.getLevel().intValue() >= seb.getLogLevel().intValue())
			storeLog(event);
	}
	
	@Override
	public void afterSebQuit(AfterSebQuitEvent event) {
		storeLogs();
	}
	
	protected void storeLog(LogEvent event) {
		String log = formatLog(new Date(Timestamp.valueOf(event.getTime()).getTime()), event.getLevel(), buildEventMessage(event));
		System.out.print(log);
		logs.add(log);
	}
	
	protected void storeLogs() {
		StringBuilder sb = new StringBuilder();
		logs.forEach(log -> sb.append(log));
		String logStr = sb.toString();
		if (!logStr.isEmpty())
			seb.saveFile(logStr, "seb", "log");
	}

	protected String buildEventMessage(LogEvent event) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s: %s", event.getName(), event.getMessage()).trim());

		if (event.getThrowable() != null) {
			sb.append("\n");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			event.getThrowable().printStackTrace(pw);
			sb.append(sw.getBuffer());
		}

		return sb.toString();
	}
	
	protected String formatLog(Date time, Level level, String message) {
		return (LOG_DATE_FORMAT.format(time) + " " + String.format("%1$-7s", level) + " " + message).trim() + "\n";
	}

}
