package examples.showcase.version;

import org.junit.Test;

public class VersionTest {

	@Test
	public void version() {
		final String file = "E:\\workspace\\mbox\\mbox-commons\\target\\classes\\cn\\yicha\\mbox\\commons\\MBoxConfig.class";
		String version = ClassVersionUtil.getVersion(file, true, true);
		System.out.println(version);
	}
}
