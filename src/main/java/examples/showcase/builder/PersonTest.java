package examples.showcase.builder;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import examples.showcase.builder.Person.Builder;

public class PersonTest {

	private static final Logger logger = LogManager.getLogger(PersonTest.class);

	@Test
	public void test() throws ParseException {
		Person person = new Builder().build();
		logger.info(person);

		Date date = DateUtils.parseDate("1986-01-31", "yyyy-MM-dd");
		person = new Builder().setFirstName("andy").setLastName("gulin").setGender(1).setAge(30).setBirthday(date)
				.setAddress("pudong").setCity("shanghai").build();
		logger.info(person);
	}
}