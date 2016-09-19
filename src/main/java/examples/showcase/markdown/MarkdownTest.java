package examples.showcase.markdown;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import examples.showcase.memcached.MemcachedTest;

public class MarkdownTest {

	private static final Logger logger = LogManager.getLogger(MemcachedTest.class);

	@Test
	public void process() throws IOException {
		Resource resource = new ClassPathResource("examples/showcase/markdown/test.md");
		String text = new Markdown4jProcessor().process(resource.getFile());
		logger.info(text);
		File file = new File(FileUtils.getTempDirectoryPath(), "test.html");
		FileUtils.writeStringToFile(file, text, "UTF-8", false);
		logger.info(file);
	}
}