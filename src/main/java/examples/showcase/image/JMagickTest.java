package examples.showcase.image;

import java.io.File;
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.junit.Before;
import org.junit.Test;

public class JMagickTest extends ImageBaseTest {

	@Before
	public void init() {
		ProcessStarter.setGlobalSearchPath("E:\\ImageMagick");
	}

	@Test
	public void resize() {
		File dest = new File(BASE_DIR, "1_800x600_4.jpg");

		ConvertCmd cmd = new ConvertCmd();

		IMOperation op = new IMOperation();
		op.strip();
		op.quality(75d);
		op.addImage(source.getPath());
		op.resize(800, 600);
		op.addImage(dest.getPath());

		try {
			cmd.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
	}
}
