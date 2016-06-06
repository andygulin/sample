package examples.showcase.serializable;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import examples.showcase.User;

public abstract class BaseTest {

	protected static final Logger logger = LogManager.getLogger();
	protected User user = null;

	@Before
	public void init() {
		user = new User(1, "小明", 11, "上海", new Date());
	}

	protected void print(Object obj) {
		logger.info(obj);
	}
}
