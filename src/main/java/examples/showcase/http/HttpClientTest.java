package examples.showcase.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class HttpClientTest extends AbstractHttpTest {

	private static final Logger logger = LogManager.getLogger(HttpClientTest.class);

	@Test
	public void request() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = httpResponse.getEntity();
			InputStream is = entity.getContent();
			String result = IOUtils.toString(is, "UTF-8");
			EntityUtils.consume(entity);
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(httpResponse);
			logger.info(result);
		}
	}
}
