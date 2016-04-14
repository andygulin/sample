package examples.showcase;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.zeroturnaround.zip.ZipInfoCallback;
import org.zeroturnaround.zip.ZipUtil;

public class ZipTest {

	private static final String zipDir = "/tmp/dubbo";
	private static final String zipFile = "/tmp/dubbo.zip";
	private static final String unZipDir = "/tmp/dubbo-master";

	@Test
	public void pack() {
		ZipUtil.pack(new File(zipDir), new File(zipFile));
	}

	@Test
	public void unpack() {
		ZipUtil.unpack(new File(zipFile), new File(unZipDir));
	}

	@Test
	public void iterate() {
		ZipUtil.iterate(new File(zipFile), new ZipInfoCallback() {
			@Override
			public void process(ZipEntry entry) throws IOException {
				if (!entry.isDirectory()) {
					System.out.println("file : " + entry.getName());
					System.out.println("before : " + entry.getSize());
					System.out.println("after : " + entry.getCompressedSize());
					System.out.println(StringUtils.repeat("=", 30));
				}
			}
		});
	}
}
