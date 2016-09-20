package examples.showcase.image;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public abstract class ImageBaseTest {

	protected static final String BASE_DIR = FileUtils.getTempDirectoryPath();

	protected File source = null;
	protected File watermark = null;

	@Before
	public void before() throws IOException {
		Resource resource = new ClassPathResource("images/1.jpg");
		source = new File(BASE_DIR, "1.jpg");
		FileUtils.copyFile(resource.getFile(), source);

		resource = new ClassPathResource("images/2.jpg");
		watermark = new File(BASE_DIR, "2.jpg");
		FileUtils.copyFile(resource.getFile(), watermark);
	}

	@After
	public void clean() {
		FileUtils.deleteQuietly(source);
		FileUtils.deleteQuietly(watermark);
	}

}
