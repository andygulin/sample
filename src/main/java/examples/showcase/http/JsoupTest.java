package examples.showcase.http;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.Test;

public class JsoupTest extends AbstractHttpTest {

	@Test
	public void request() throws IOException {
		String html = Jsoup.connect(url).get().html();
		System.out.println(html);
	}
}
