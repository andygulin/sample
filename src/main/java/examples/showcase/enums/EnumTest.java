package examples.showcase.enums;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class EnumTest {

	private static final Logger logger = LogManager.getLogger(EnumTest.class);

	@Test
	public void test() {
		logger.info(NameEnum.AA.getName());
		logger.info(NameEnum.BB.getName());
		logger.info(NameEnum.CC.getName());
	}
}