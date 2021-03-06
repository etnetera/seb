/* Copyright 2016 Etnetera a.s.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.etnetera.seb;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Common helper methods.
 */
public class SebUtils {

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
	
	public Path getFilePath(File root, String name, String extension) {
		return root.toPath().resolve(join(".", name, extension));
	}

	public Path getUniqueFilePath(File root, String name, String extension) {
		name = escapeFileName(name);
		Path path = getFilePath(root, name, extension);
		int suffix = 0;
		while (path.toFile().exists()) {
			path = getFilePath(root, join(Seb.LABEL_DELIMITER, name, ++suffix), extension);
		}
		return path;
	}

	public String escapeFileName(String name) {
		return name.replaceAll("[^a-zA-Z0-9_\\-\\." + Pattern.quote(File.separator) + "]", "_");
	}
	
}
