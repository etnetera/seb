package cz.etnetera.seb.listener.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

import cz.etnetera.seb.Seb;
import cz.etnetera.seb.SebException;
import cz.etnetera.seb.event.impl.BeforeSebQuitEvent;
import cz.etnetera.seb.listener.SebListener;

public class ConfigListener extends SebListener {

	@SuppressWarnings("unchecked")
	@Override
	public void init(Seb seb) {
		super.init(seb);
		enable(BeforeSebQuitEvent.class);
	}

	@Override
	public void beforeSebQuit(BeforeSebQuitEvent event) {
		Properties tmp = new Properties() {
			private static final long serialVersionUID = 3324860429625674641L;

			@Override
			public synchronized Enumeration<Object> keys() {
				return Collections.enumeration(new TreeSet<Object>(super.keySet()));
			}
		};
		tmp.putAll(seb.getConfiguration().asMap());
		StringWriter sw = new StringWriter();
		try {
			tmp.store(sw, null);
		} catch (IOException e) {
			throw new SebException("Unable to write configuration map into string writer.");
		}
		seb.saveFile(sw.getBuffer().toString(), "config", "properties");
	}

}
