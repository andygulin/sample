package examples.showcase;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.builders.ThumbnailParameterBuilder;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.tasks.FileThumbnailTask;

public class ThumbnailsTest {

	private File source = null;
	private File watermark = null;

	@Before
	public void before() throws IOException {
		Resource resource = new ClassPathResource("images/1.jpg");
		source = new File(SystemUtils.IS_OS_WINDOWS ? "C:/1.jpg" : "/tmp/1.jpg");
		FileUtils.copyFile(resource.getFile(), source);

		resource = new ClassPathResource("images/2.jpg");
		watermark = new File(SystemUtils.IS_OS_WINDOWS ? "C:/2.jpg" : "/tmp/2.jpg");
		FileUtils.copyFile(resource.getFile(), watermark);
	}

	@Test
	public void thumbnails() throws IOException {
		File dest1 = new File(SystemUtils.IS_OS_WINDOWS ? "C:/1_800x600_1.jpg" : "/tmp/1_800x600_1.jpg");
		File dest2 = new File(SystemUtils.IS_OS_WINDOWS ? "C:/1_800x600_2.jpg" : "/tmp/1_800x600_2.jpg");
		Thumbnails.of(source).size(800, 600).toFile(dest1);
		Thumbnailator.createThumbnail(source, dest2, 800, 600);
	}

	@Test
	public void watermark() throws IOException {
		Thumbnailator.createThumbnail(watermark, watermark, 100, 100);
		File dest3 = new File(SystemUtils.IS_OS_WINDOWS ? "C:/1_watermark.jpg" : "/tmp/1_watermark.jpg");
		Thumbnails.of(source).size(800, 600).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.5f)
				.outputQuality(0.8f).toFile(dest3);
	}

	@Test
	public void fileThumbnailTask() throws IOException {
		ThumbnailParameter parameter = new ThumbnailParameterBuilder().size(800, 600).build();

		Thumbnailator.createThumbnail(new FileThumbnailTask(parameter, source,
				new File(SystemUtils.IS_OS_WINDOWS ? "C:/dest3.jpg" : "/tmp/dest3.jpg")));
	}

	@After
	public void clean() {
		FileUtils.deleteQuietly(source);
		FileUtils.deleteQuietly(watermark);
	}
}
