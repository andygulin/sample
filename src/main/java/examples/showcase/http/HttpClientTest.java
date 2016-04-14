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
import org.junit.Test;

public class HttpClientTest extends AbstractHttpTest {

	@Test
	public void request() throws ClientProtocolException, IOException {
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
			System.out.println(result);
		}
	}
}
