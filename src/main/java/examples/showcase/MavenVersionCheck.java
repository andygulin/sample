package examples.showcase;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

public class MavenVersionCheck {

	private static Map<String, String> URLS = Maps.newHashMap();
	private static final String BASE_URL = "http://www.mvnrepository.com/artifact/";

	static {
		Resource resource = new ClassPathResource("maven-version-check.txt");
		try {
			List<String> lines = Files.readLines(resource.getFile(), Charset.forName("UTF-8"));
			for (String line : lines) {
				if (StringUtils.isNotEmpty(line)) {
					String key = line.substring(line.lastIndexOf("/") + 1);
					String value = BASE_URL + line;
					URLS.put(key, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final int POOL_SIZE = 10;
		ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
		for (Entry<String, String> data : URLS.entrySet()) {
			if (StringUtils.isNotEmpty(data.getKey()) && StringUtils.isNoneEmpty(data.getValue())) {
				service.execute(new RequestVersion(data));
			}
		}
		service.shutdown();
	}
}

class RequestVersion implements Runnable {

	private static final Logger logger = LogManager.getLogger(RequestVersion.class);

	private Entry<String, String> data;

	public RequestVersion(Entry<String, String> data) {
		this.data = data;
	}

	@Override
	public void run() {
		String key = data.getKey();
		String value = data.getValue();
		Document doc = null;
		try {
			doc = Jsoup.connect(value).timeout(Integer.MAX_VALUE).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Element> lists = Lists.newArrayList();
		Elements els = doc.select("a.vbtn.release");
		if (els != null && els.size() > 0) {
			lists = els.subList(0, 1);
			logger.info(key + " -> " + lists.get(0).text());
			logger.info(StringUtils.repeat("*", 80));
		} else {
			logger.info("no release version");
		}
	}
}
