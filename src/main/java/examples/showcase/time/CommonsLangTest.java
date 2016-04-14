package examples.showcase.time;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class CommonsLangTest {

	private static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String TIME_PATTERN = "HH:mm:ss";
	private static final Date date = new Date();

	@Test
	public void formatTest() {
		System.out.println(DateFormatUtils.format(date, FULL_PATTERN));
		System.out.println(DateFormatUtils.format(Calendar.getInstance(), FULL_PATTERN));
		System.out.println(DateFormatUtils.format(date.getTime(), FULL_PATTERN));
		System.out.println(DateFormatUtils.format(date, FULL_PATTERN, TimeZone.getDefault(), Locale.CHINESE));
	}

	@Test
	public void dateTest() {
		System.out.println(DateFormatUtils.format(DateUtils.addDays(date, 10), FULL_PATTERN));
		System.out.println(DateFormatUtils.format(DateUtils.addDays(date, -10), FULL_PATTERN));

		System.out.println(DateUtils.isSameDay(date, DateUtils.addDays(date, -1)));
		System.out.println(DateUtils.isSameDay(date, DateUtils.addHours(date, 2)));
	}

	@Test
	public void parseTest() throws ParseException {
		final String[] parsePatterns = { FULL_PATTERN, DATE_PATTERN, TIME_PATTERN };
		Date date = DateUtils.parseDate("2015-10-10 12:10:23", parsePatterns);
		System.out.println(date);
		date = DateUtils.parseDate("2015-10-10", parsePatterns);
		System.out.println(date);
		date = DateUtils.parseDate("12:10:23", parsePatterns);
		System.out.println(date);
	}

	@Test
	public void rangeTest() {
		Iterator<Calendar> iter = DateUtils.iterator(date, DateUtils.RANGE_WEEK_MONDAY);
		while (iter.hasNext()) {
			Calendar cal = iter.next();
			System.out.println(DateFormatUtils.format(cal, FULL_PATTERN));
		}
		System.out.println(StringUtils.repeat("-", 80));
		iter = DateUtils.iterator(date, DateUtils.RANGE_MONTH_MONDAY);
		while (iter.hasNext()) {
			Calendar cal = iter.next();
			System.out.println(DateFormatUtils.format(cal, FULL_PATTERN));
		}
	}
}
