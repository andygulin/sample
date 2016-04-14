package examples.showcase.time;

import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;

public class JodaTest {

	private static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final DateTime dateTime = new DateTime();

	@Test
	public void formatTest() {
		System.out.println(dateTime.toString(FULL_PATTERN));
		System.out.println(dateTime.toString(FULL_PATTERN, Locale.CHINESE));
	}

	@Test
	public void dateTest() {
		System.out.println(dateTime.plusDays(10).toString(FULL_PATTERN));
		System.out.println(dateTime.plusDays(-10).toString(FULL_PATTERN));
	}
}