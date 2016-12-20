package examples.showcase.version;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class VersionTest {

    private static final Logger logger = LogManager.getLogger(VersionTest.class);

    @Test
    public void version() {
        final String file = "E:\\workspace\\mbox\\mbox-commons\\target\\classes\\cn\\yicha\\mbox\\commons\\MBoxConfig.class";
        String version = ClassVersionUtil.getVersion(file, true, true);
        logger.info(version);
    }
}