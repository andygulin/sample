package examples.showcase.time;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;

public class JodaTest {

	private static final Logger logger = LogManager.getLogger(JodaTest.class);

	private static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final DateTime dateTime = new DateTime();

	@Test
	public void formatTest() {
		logger.info(dateTime.toString(FULL_PATTERN));
		logger.info(dateTime.toString(FULL_PATTERN, Locale.CHINESE));
	}

	@Test
	public void dateTest() {
		logger.info(dateTime.plusDays(10).toString(FULL_PATTERN));
		logger.info(dateTime.plusDays(-10).toString(FULL_PATTERN));
	}
}