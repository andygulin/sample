package examples.showcase.sphinx.test;

import java.util.Arrays;

import org.junit.Test;

import examples.showcase.sphinx.service.SphinxService;

public class SphinxTest {

	@Test
	public void search() {
		long start = System.currentTimeMillis();
		Long[] ids = SphinxService.getInstance().getMatchIds(new String[] { "黄色", "网站" }, 0);
		long end = System.currentTimeMillis();
		System.out.println("用时: " + (end - start));
		System.out.println("匹配数 " + ids.length);
		System.out.println(Arrays.toString(ids));
	}
}
