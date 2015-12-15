package com.etnetera.qa.seleniumbrowser.browser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrowserUtils {

	public String join(String delimiter, Object... values) {
		return join(delimiter, Arrays.stream(values));
	}
	
	public String join(String delimiter, List<Object> values) {
		return join(delimiter, values.stream());
	}
	
	public String join(String delimiter, Stream<Object> values) {
		String joined = values.filter(Objects::nonNull).map(v -> String.valueOf(v)).collect(Collectors.joining(delimiter));
		return joined.length() > 0 ? joined : null;
	}
	
}
