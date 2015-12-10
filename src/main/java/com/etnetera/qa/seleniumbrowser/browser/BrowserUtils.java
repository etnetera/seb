package com.etnetera.qa.seleniumbrowser.browser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrowserUtils {

	public static String join(String delimiter, Object... values) {
		return join(delimiter, Arrays.stream(values));
	}
	
	public static String join(String delimiter, List<Object> values) {
		return join(delimiter, values.stream());
	}
	
	public static String join(String delimiter, Stream<Object> values) {
		String joined = values.filter(Objects::nonNull).map(v -> String.valueOf(v)).collect(Collectors.joining(delimiter));
		return joined.length() > 0 ? joined : null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Object> T castString(String value, Class<T> defCls) {
		if (value == null) return null;
		if (String.class.isAssignableFrom(defCls)) {
			return (T) value;
		}
		if (Boolean.class.isAssignableFrom(defCls)) {
			return (T) Boolean.valueOf(value);
		}
		if (Byte.class.isAssignableFrom(defCls)) {
			return (T) Byte.valueOf(value);
		}
		if (Character.class.isAssignableFrom(defCls)) {
			return (T) Character.valueOf(value.charAt(0));
		}
		if (Double.class.isAssignableFrom(defCls)) {
			return (T) Double.valueOf(value);
		}
		if (Float.class.isAssignableFrom(defCls)) {
			return (T) Float.valueOf(value);
		}
		if (Integer.class.isAssignableFrom(defCls)) {
			return (T) Integer.valueOf(value);
		}
		if (Long.class.isAssignableFrom(defCls)) {
			return (T) Long.valueOf(value);
		}
		if (Short.class.isAssignableFrom(defCls)) {
			return (T) Short.valueOf(value);
		}
		if (File.class.isAssignableFrom(defCls)) {
			return (T) new File(value);
		}
		if (Path.class.isAssignableFrom(defCls)) {
			return (T) Paths.get(value);
		}
		if (defCls.isEnum()) {
			return (T) Enum.valueOf((Class<Enum>) defCls, value);
		}
		throw new BrowserException("Unsupported string cast type " + defCls.getName() + " for value " + value);
	}
	
}
