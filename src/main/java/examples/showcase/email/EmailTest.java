package examples.showcase.email;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EmailTest {

    private static final Logger logger = LogManager.getLogger(EmailTest.class);

    @Test
    public void send() {
        List<String> tos = new ArrayList<>();
        tos.add("610603860@qq.com");
        List<File> attachments = (List<File>) FileUtils.listFiles(new File("/home/gulin/图片"), new String[]{"jpg"},
                false);
        boolean result = EmailUtil.sendMail(tos, "javax.mail", "邮件的内容", attachments);
        if (result) {
            logger.info("OK");
        } else {
            logger.info("FALL");
        }
    }
}
