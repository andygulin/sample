package examples.showcase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class IOTest {

	private static final Logger logger = LogManager.getLogger(IOTest.class);

	private static final int BUFFER_SIZE = 512;

	@Test
	public void newFile() throws IOException {
		File file = new File("/tmp", "empty");
		Assert.assertEquals(file.createNewFile(), true);
	}

	@Test
	public void fileOutput() throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("/tmp", "aaa")));
		out.write(new String("aaa").getBytes());
		out.flush();
		out.close();
	}

	@Test
	public void fileInput() throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File("/tmp", "aaa")));
		byte[] b = new byte[BUFFER_SIZE];
		while ((in.read(b)) != -1) {
			logger.info(new String(b));
		}
		in.close();
	}

	@Test
	public void byteArray() throws IOException {
	}
}