package examples.showcase.image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.builders.ThumbnailParameterBuilder;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.tasks.FileThumbnailTask;

public class ThumbnailatorTest extends ImageBaseTest {

	@Test
	public void thumbnails() throws IOException {
		File dest1 = new File(BASE_DIR, "1_800x600_1.jpg");
		File dest2 = new File(BASE_DIR, "1_800x600_2.jpg");
		Thumbnails.of(source).size(800, 600).toFile(dest1);
		Thumbnailator.createThumbnail(source, dest2, 800, 600);
	}

	@Test
	public void watermark() throws IOException {
		Thumbnailator.createThumbnail(watermark, watermark, 100, 100);
		File dest3 = new File(BASE_DIR, "1_watermark.jpg");
		Thumbnails.of(source).size(800, 600).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.5f)
				.outputQuality(0.8f).toFile(dest3);
	}

	@Test
	public void fileThumbnailTask() throws IOException {
		File dest3 = new File(BASE_DIR, "1_800x600_3.jpg");
		ThumbnailParameter parameter = new ThumbnailParameterBuilder().size(800, 600).build();
		Thumbnailator.createThumbnail(new FileThumbnailTask(parameter, source, dest3));
	}
}