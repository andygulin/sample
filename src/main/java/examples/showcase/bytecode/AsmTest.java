package examples.showcase.bytecode;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

public class AsmTest {

	private static final Logger logger = LogManager.getLogger(AsmTest.class);

	@Test
	public void read() throws IOException {
		ClassReader reader = new ClassReader("examples/showcase/User");
		logger.info(reader);
	}
}