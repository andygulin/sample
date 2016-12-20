package examples.showcase.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

public class JsoupTest extends AbstractHttpTest {

    private static final Logger logger = LogManager.getLogger(JsoupTest.class);

    @Test
    public void request() throws IOException {
        String html = Jsoup.connect(url).get().html();
        logger.info(html);
    }
}
