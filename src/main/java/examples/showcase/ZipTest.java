package examples.showcase;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class ZipTest {

    private static final Logger logger = LogManager.getLogger(ZipTest.class);

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
        ZipUtil.iterate(new File(zipFile), (entry -> {
            if (entry.isDirectory()) {
                logger.info("file : " + entry.getName());
                logger.info("before : " + entry.getSize());
                logger.info("after : " + entry.getCompressedSize());
                logger.info(StringUtils.repeat("=", 30));
            }
        }
        ));
    }
}
