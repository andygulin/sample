package examples.showcase.sphinx.test;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import examples.showcase.sphinx.service.SphinxService;

public class SphinxTest {

	private static final Logger logger = LogManager.getLogger(SphinxTest.class);

	@Test
	public void search() {
		long start = System.currentTimeMillis();
		Long[] ids = SphinxService.getInstance().getMatchIds(new String[] { "黄色", "网站" }, 0);
		long end = System.currentTimeMillis();
		logger.info("用时: " + (end - start));
		logger.info("匹配数 " + ids.length);
		logger.info(Arrays.toString(ids));
	}
}
