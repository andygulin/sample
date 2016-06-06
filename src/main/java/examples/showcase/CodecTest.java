package examples.showcase;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.RefinedSoundex;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class CodecTest {

	private static final Logger logger = LogManager.getLogger(CodecTest.class);

	private final String str = "hello";
	private final File file = new File("pom.xml");

	@Test
	public void md5() throws Exception {
		hex("md5Hex");
	}

	@Test
	public void md2() throws Exception {
		hex("md2Hex");
	}

	@Test
	public void base64() {
		byte[] b = Base64.encodeBase64(str.getBytes());
		logger.info(new String(b));
		byte[] s = Base64.decodeBase64(b);
		logger.info(new String(s));
	}

	private void hex(String methodName) throws Exception {
		String result = (String) MethodUtils.invokeStaticMethod(DigestUtils.class, methodName, str);
		logger.info("string: " + str + " -> " + result);

		InputStream is = FileUtils.openInputStream(file);
		result = (String) MethodUtils.invokeStaticMethod(DigestUtils.class, methodName, is);
		logger.info("file: " + file.getName() + " -> " + result);
	}

	@Test
	public void sha1() throws Exception {
		hex("sha1Hex");
	}

	@Test
	public void sha256() throws Exception {
		hex("sha256Hex");
	}

	@Test
	public void sha384() throws Exception {
		hex("sha384Hex");
	}

	@Test
	public void sha512() throws Exception {
		hex("sha512Hex");
	}

	private void sound(Class<? extends StringEncoder> clazz) throws Exception {
		StringEncoder encoder = (StringEncoder) clazz.newInstance();
		logger.info("hello:" + encoder.encode("hello"));
		logger.info("world: " + encoder.encode("world"));
		logger.info("extend: " + encoder.encode("extend"));
	}

	@Test
	public void metaphone() throws Exception {
		sound(Metaphone.class);
	}

	@Test
	public void soundex() throws Exception {
		sound(Soundex.class);
	}

	@Test
	public void refinedSoundex() throws Exception {
		sound(RefinedSoundex.class);
	}
}
