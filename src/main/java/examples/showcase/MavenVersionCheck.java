package examples.showcase;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MavenVersionCheck {

    private static final String BASE_URL = "http://www.mvnrepository.com/artifact/";
    private static List<RequestMessage> requestMessages = new ArrayList<>();

    static {
        Resource resource = new ClassPathResource("maven-version-check.txt");
        try {
            List<String> lines = Files.readLines(resource.getFile(), Charset.forName("UTF-8"));
            for (String line : lines) {
                if (StringUtils.isNotEmpty(line)) {
                    String name = line.substring(line.lastIndexOf("/") + 1);
                    String url = BASE_URL + line;
                    RequestMessage requestMessage = new RequestMessage();
                    requestMessage.setName(name);
                    requestMessage.setUrl(url);
                    requestMessages.add(requestMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final int POOL_SIZE = 10;
        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
        List<List<RequestMessage>> lists = Lists.partition(requestMessages, 10);
        for (List<RequestMessage> data : lists) {
            service.execute(new RequestVersion(data));
        }
        service.shutdown();
    }

    private static class RequestVersion implements Runnable {

        private static final Logger logger = LogManager.getLogger(RequestVersion.class);

        private List<RequestMessage> data;

        public RequestVersion(List<RequestMessage> data) {
            this.data = data;
        }

        @Override
        public void run() {
            for (RequestMessage message : data) {
                String name = message.getName();
                String url = message.getUrl();
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).timeout(Integer.MAX_VALUE).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<Element> lists = Lists.newArrayList();
                Elements els = doc.select("a.vbtn.release");
                if (els != null && els.size() > 0) {
                    lists = els.subList(0, 1);
                    logger.info(name + " -> " + lists.get(0).text());
                    logger.info(StringUtils.repeat("*", 80));
                } else {
                    logger.info("no release version");
                }
            }
        }
    }

    private static class RequestMessage implements Serializable {

        private static final long serialVersionUID = -4051813344332347116L;

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
