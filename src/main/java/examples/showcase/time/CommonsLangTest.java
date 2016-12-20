package examples.showcase.time;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.text.ParseException;
import java.util.*;

public class CommonsLangTest {

    private static final Logger logger = LogManager.getLogger(CommonsLangTest.class);

    private static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final Date date = new Date();

    @Test
    public void formatTest() {
        logger.info(DateFormatUtils.format(date, FULL_PATTERN));
        logger.info(DateFormatUtils.format(Calendar.getInstance(), FULL_PATTERN));
        logger.info(DateFormatUtils.format(date.getTime(), FULL_PATTERN));
        logger.info(DateFormatUtils.format(date, FULL_PATTERN, TimeZone.getDefault(), Locale.CHINESE));
    }

    @Test
    public void dateTest() {
        logger.info(DateFormatUtils.format(DateUtils.addDays(date, 10), FULL_PATTERN));
        logger.info(DateFormatUtils.format(DateUtils.addDays(date, -10), FULL_PATTERN));

        logger.info(DateUtils.isSameDay(date, DateUtils.addDays(date, -1)));
        logger.info(DateUtils.isSameDay(date, DateUtils.addHours(date, 2)));
    }

    @Test
    public void parseTest() throws ParseException {
        final String[] parsePatterns = {FULL_PATTERN, DATE_PATTERN, TIME_PATTERN};
        Date date = DateUtils.parseDate("2015-10-10 12:10:23", parsePatterns);
        logger.info(date);
        date = DateUtils.parseDate("2015-10-10", parsePatterns);
        logger.info(date);
        date = DateUtils.parseDate("12:10:23", parsePatterns);
        logger.info(date);
    }

    @Test
    public void rangeTest() {
        Iterator<Calendar> iter = DateUtils.iterator(date, DateUtils.RANGE_WEEK_MONDAY);
        while (iter.hasNext()) {
            Calendar cal = iter.next();
            logger.info(DateFormatUtils.format(cal, FULL_PATTERN));
        }
        logger.info(StringUtils.repeat("-", 80));
        iter = DateUtils.iterator(date, DateUtils.RANGE_MONTH_MONDAY);
        while (iter.hasNext()) {
            Calendar cal = iter.next();
            logger.info(DateFormatUtils.format(cal, FULL_PATTERN));
        }
    }
}
