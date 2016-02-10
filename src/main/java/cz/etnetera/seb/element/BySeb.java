package cz.etnetera.seb.element;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * Seb provided By mechanisms.
 */
public class BySeb {

	/**
	 * Finds elements via custom callback which accepts {@link SearchContext}
	 * and returns {@link List} of {@link WebElement}s.
	 *
	 * @param callback callback
	 * @return a By which locates elements by callback.
	 */
	public static By callback(final Function<SearchContext, List<WebElement>> callback) {
		if (callback == null)
			throw new IllegalArgumentException("Cannot find elements when the callback is null");

		return new ByCallback(callback);
	}

	public static class ByCallback extends By implements Serializable {

		private static final long serialVersionUID = 8378470466668235992L;

		protected final Function<SearchContext, List<WebElement>> callback;

		public ByCallback(Function<SearchContext, List<WebElement>> callback) {
			this.callback = callback;
		}

		@Override
		public List<WebElement> findElements(SearchContext context) {
			return callback.apply(context);
		}

		@Override
		public String toString() {
			return "By.callback: " + callback.toString();
		}

	}

}
