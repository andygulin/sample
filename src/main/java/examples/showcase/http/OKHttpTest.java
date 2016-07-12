package examples.showcase.http;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpTest extends AbstractHttpTest {

	private static final Logger logger = LogManager.getLogger(OKHttpTest.class);

	@Test
	public void request() throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			logger.info(response.body().string());
		}
	}
}
